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

package com.android.build.gradle.internal.core;

import com.android.annotations.NonNull;
import com.android.build.gradle.internal.dsl.CoreJackOptions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Implementation of CoreJackOptions used to merge multiple configs together.
 */
public class MergedJackOptions implements CoreJackOptions {

    private boolean isEnabledFlag = false;

    private boolean isJackInProcessFlag = true;
    
    @NonNull
    private List<File> pluginPath = Lists.newArrayList();
    
    @NonNull
    private List<String> plugins = Lists.newArrayList();

    @NonNull
    private Map<String, String> additionalParameters = Maps.newHashMap();

    public void reset() {
        isEnabledFlag = false;
        isJackInProcessFlag = true;
        plugins.clear();
        additionalParameters.clear();
    }

    public void append(@NonNull CoreJackOptions that) {
        if (that.isEnabled() != null) {
            //noinspection ConstantConditions - Idea unable to infer that RHS is @NonNull
            isEnabledFlag = that.isEnabled();
        }
        if (that.isJackInProcess() != null) {
            //noinspection ConstantConditions - the same as above
            isJackInProcessFlag = that.isJackInProcess();
        }
        pluginPath.addAll(that.getPluginPath());
        plugins.addAll(that.getPlugins());
        additionalParameters.putAll(that.getAdditionalParameters());
    }

    @Override
    @NonNull
    public Boolean isEnabled() {
        return isEnabledFlag;
    }

    @Override
    @NonNull
    public Boolean isJackInProcess() {
        return isJackInProcessFlag;
    }

    @Override
    public List<File> getPluginPath() {
        return pluginPath;
    }

    @Override
    public List<String> getPlugins() {
        return plugins;
    }

    @Override
    @NonNull
    public Map<String, String> getAdditionalParameters() {
        return additionalParameters;
    }
}
