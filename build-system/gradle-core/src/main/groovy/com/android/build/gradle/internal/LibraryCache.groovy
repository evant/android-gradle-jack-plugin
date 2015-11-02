/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.build.gradle.internal
import com.android.annotations.NonNull
import com.android.annotations.concurrency.GuardedBy
import com.google.common.collect.Maps
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.api.file.FileCopyDetails
import org.gradle.api.file.RelativePath

import java.util.concurrent.CountDownLatch

import static com.android.SdkConstants.FD_JARS
/**
 * Cache to library prepareTask.
 *
 * Each project creates its own version of LibraryDependencyImpl, but they all represent the
 * same library. This creates a single task that will unarchive the aar so that this is done only
 * once even for multi-module projects where 2+ modules depend on the same library.
 *
 * The prepareTask is created in the root project always.
 */
public class LibraryCache {

    @NonNull
    private static final LibraryCache sCache = new LibraryCache()

    @NonNull
    public static LibraryCache getCache() {
        return sCache
    }

    public synchronized unload() {
        bundleLatches.clear();
    }

    @GuardedBy("this")
    private final Map<String, CountDownLatch> bundleLatches = Maps.newHashMap()

    public void unzipLibrary(
            @NonNull String taskName,
            @NonNull Project project,
            @NonNull File bundle,
            @NonNull File folderOut) {

        // only synchronize access to the latch so that unzipping 2+ different
        // libraries in parallel will work.
        boolean newItem = false;
        CountDownLatch latch;
        synchronized (this) {
            String path = bundle.getCanonicalPath()
            latch = bundleLatches.get(path)
            if (latch == null) {
                latch = new CountDownLatch(1)
                bundleLatches.put(path, latch)
                newItem = true
            }
        }

        if (newItem) {
            try {
                project.logger.debug("$taskName: ERASE ${folderOut.getPath()}")

                unzipAar(bundle, folderOut, project)

                project.logger.debug("$taskName: UNZIP ${bundle.getPath()} -> ${folderOut.getPath()}")
            } finally {
                latch.countDown()
            }
        } else {
            latch.await()
        }
    }

    public static void unzipAar(File bundle, File folderOut, Project project) {
        folderOut.deleteDir()
        folderOut.mkdirs()

        project.copy(new Action<CopySpec>() {
            @Override
            void execute(CopySpec spec) {
                spec.from(project.zipTree(bundle));
                spec.into(folderOut);
                spec.filesMatching("**/*.jar", new Action<FileCopyDetails>() {
                    @Override
                    void execute(FileCopyDetails details) {
                        /*
                         * For each jar, check where it is. /classes.jar, /lint.jar and jars in
                         * /libs are moved inside the FD_JARS directory. Jars inside /assets or
                         * /res/raw are kept where they were. All other jars are ignored and a
                         * warning is issued.
                         */
                        String path = details.getRelativePath().getPathString();
                        if (path.equals("classes.jar") || path.equals("lint.jar")
                                || path.startsWith("libs/")) {
                            details.setRelativePath(new RelativePath(false, FD_JARS).plus(
                                    details.relativePath));
                        } else if (!path.startsWith("res/raw/*") && !path.startsWith("assets/*")) {
                            project.getLogger().warn("Jar found at unexpected path (" + path
                                    + ") in " + bundle + " and will be ignored. Jars should be "
                                    + "placed inside 'jars' folder to be merged into dex. Jars "
                                    + "that are in assets/ or res/raw/ will be copied as-is.");
                        }
                    }
                });
            }
        });
    }
}
