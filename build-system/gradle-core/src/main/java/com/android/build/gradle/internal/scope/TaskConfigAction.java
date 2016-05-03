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

package com.android.build.gradle.internal.scope;

import com.android.annotations.NonNull;

import org.gradle.api.Action;

import java.util.function.Consumer;

/**
 * Interface of Task configuration Actions.
 */
public interface TaskConfigAction<T> extends Action<T> {

    /**
     * Return the name of the task to be configured.
     */
    @NonNull
    String getName();

    /**
     * Return the class type of the task to be configured.
     */
    @NonNull
    Class<T> getType();

    /**
     * Configure the given newly-created task object.
     */
    @Override
    void execute(@NonNull T task);

    /**
     * Returns a new {@link TaskConfigAction} which executes additional steps after this action
     * is done.
     */
    default TaskConfigAction<T> andThen(Consumer<T> additionalActions) {
        return new TaskConfigAction<T>() {
            @NonNull
            @Override
            public String getName() {
                return TaskConfigAction.this.getName();
            }

            @NonNull
            @Override
            public Class<T> getType() {
                return TaskConfigAction.this.getType();
            }

            @Override
            public void execute(@NonNull T task) {
                TaskConfigAction.this.execute(task);
                additionalActions.accept(task);
            }
        };
    }
}
