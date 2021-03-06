/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.android.build.gradle.managed;

import com.android.annotations.NonNull;

import org.gradle.model.Managed;
import org.gradle.model.ModelMap;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Managed type for Jack options.
 */
@Managed
public interface JackOptions {
    /**
     * Returns whether to use Jack for compilation.
     *
     * <p>See <a href="http://tools.android.com/tech-docs/jackandjill">Jack and Jill</a>
     */
    Boolean getEnabled();

    void setEnabled(Boolean enabled);

    /**
     * Returns whether to run Jack the same JVM as Gradle.
     */
    Boolean getJackInProcess();

    void setJackInProcess(Boolean jackInProcess);
    
    List<File> getPluginPath();
    
    List<String> getPlugins();

    /**
     * Additional parameters to be passed to Jack.
     */
    @NonNull
    ModelMap<KeyValuePair> getAdditionalParameters();
}
