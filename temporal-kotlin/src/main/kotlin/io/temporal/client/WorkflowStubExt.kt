/*
 * Copyright (C) 2022 Temporal Technologies, Inc. All Rights Reserved.
 *
 * Copyright (C) 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this material except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.temporal.client

import io.temporal.common.Experimental
import java.util.concurrent.CompletableFuture
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

/**
 * Returns workflow result potentially waiting for workflow to complete.
 *
 * @param T type of the workflow return value
 * @see WorkflowStub.getResult
 */
@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> WorkflowStub.getResult(): T {
  return getResult(T::class.java, typeOf<T>().javaType)
}

/**
 * @see WorkflowStub.getResultAsync
 */
@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> WorkflowStub.getResultAsync(): CompletableFuture<T> {
  return getResultAsync(T::class.java, typeOf<T>().javaType)
}

/**
 * @see WorkflowStub.update
 */
@Experimental
inline fun <reified R> WorkflowStub.update(updateName: String, vararg args: Any?): R {
  return update(updateName, R::class.java, *args)
}

/**
 * @see WorkflowStub.update
 */
@Experimental
@OptIn(ExperimentalStdlibApi::class)
inline fun <reified R> WorkflowStub.update(
  updateName: String,
  updateId: String,
  firstExecutionRunId: String,
  vararg args: Any?
): R {
  return update(updateName, updateId, firstExecutionRunId, R::class.java, typeOf<R>().javaType, *args)
}

/**
 * @see WorkflowStub.startUpdate
 */
@Experimental
inline fun <reified R> WorkflowStub.startUpdate(updateName: String, vararg args: Any?): UpdateHandle<R> {
  return startUpdate(updateName, R::class.java, *args)
}

/**
 * @see WorkflowStub.startUpdate
 */
@Experimental
@OptIn(ExperimentalStdlibApi::class)
inline fun <reified R> WorkflowStub.startUpdate(
  updateName: String,
  updateId: String,
  firstExecutionRunId: String,
  vararg args: Any?
): UpdateHandle<R> {
  return startUpdate(updateName, updateId, firstExecutionRunId, R::class.java, typeOf<R>().javaType, *args)
}
