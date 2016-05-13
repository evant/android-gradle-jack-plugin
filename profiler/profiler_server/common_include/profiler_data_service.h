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
#ifndef PROFILER_DATA_SERVICE_H_
#define PROFILER_DATA_SERVICE_H_

// TODO: Remove relative path of proto header files.
#include "../proto/profiler_data.pb.h"

namespace profiler_server {

// Class to provide data saving interface, this is an empty definition.
class ProfilerDataService {
 public:
   virtual ~ProfilerDataService() = default;
   virtual void save(const profiler::proto::ProfilerData &data) {}
};

} // namespace profiler_server

#endif // PROFILER_DATA_SERVICE_H_