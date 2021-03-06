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

package com.android.build.gradle.internal.dsl;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Options for configuring Jack.
 */
public interface CoreJackOptions {

    /**
     * Whether to use Jack for compilation. By default, this value is {@code false}.
     */
    @Nullable
    Boolean isEnabled();

    /**
     * Whether to run Jack the same JVM as Gradle. By default, this value is {@code true}.
     */
    @Nullable
    Boolean isJackInProcess();

    /**
     * Plugin path for jack.
     */
    List<File> getPluginPath();

    /**
     * Plugins to run jack with.
     */
    @NonNull
    List<String> getPlugins();

    /**
     * Additional parameters to be passed to Jack.
     */
    @NonNull
    Map<String, String> getAdditionalParameters();
}
