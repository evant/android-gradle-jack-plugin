/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.android.ide.common.res2;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * A {@link ResourcePreprocessor} used when no other preprocessor is enabled.
 */
public class NoOpResourcePreprocessor implements ResourcePreprocessor {

    @Override
    public boolean needsPreprocessing(File file) {
        return false;
    }

    @Override
    public Collection<File> getFilesToBeGenerated(File original) {
        throw new IllegalStateException("Should not be called");
    }

    @Override
    public void generateFile(File toBeGenerated, File original) throws IOException {
        throw new IllegalStateException("Should not be called");
    }
}
