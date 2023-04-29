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

package io.temporal.common.metadata

import io.temporal.workflow.QueryMethod
import io.temporal.workflow.SignalMethod
import io.temporal.workflow.UpdateMethod
import io.temporal.workflow.UpdateValidatorMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod
import org.junit.Assert.assertEquals
import org.junit.Test

class WorkflowMethodNameTest {

  @Test
  fun `workflowSignalName should resolve simple workflow signal name`() {
    assertEquals("signal1", workflowSignalName(Workflow1::signal1))
  }

  @Test
  fun `workflowSignalName should resolve workflow signal name override`() {
    assertEquals("customSignalName", workflowSignalName(Workflow1::signal2))
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowSignalName should fail if provided with query method instead of signal method`() {
    workflowSignalName(Workflow1::query1)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowSignalName should fail if used with non-workflow method`() {
    workflowSignalName(NotAWorkflow::aMethod)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowSignalName should fail if not provided with a method reference`() {
    workflowSignalName(::String)
  }

  @Test
  fun `workflowUpdateName should resolve simple workflow update name by update method reference`() {
    assertEquals("update1", workflowUpdateName(Workflow1::update1))
  }

  @Test
  fun `workflowUpdateName should resolve simple workflow update name by update validator method reference`() {
    assertEquals("update1", workflowUpdateName(Workflow1::update1Validator))
  }

  @Test
  fun `workflowUpdateName should resolve workflow update name override by update method reference`() {
    assertEquals("customUpdateName", workflowUpdateName(Workflow1::update2))
  }

  @Test
  fun `workflowUpdateValidatorName should resolve workflow update name override by update validator method reference`() {
    assertEquals("customUpdateName", workflowUpdateName(Workflow1::update2Validator))
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowUpdateName should fail if provided with query method instead of update method`() {
    workflowUpdateName(Workflow1::query1)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowUpdateName should fail if provided with signal method instead of update method`() {
    workflowUpdateName(Workflow1::signal1)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowUpdateName should fail if used with non-workflow method`() {
    workflowUpdateName(NotAWorkflow::aMethod)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowUpdateName should fail if not provided with a method reference`() {
    workflowUpdateName(::String)
  }

  @Test
  fun `workflowQueryType should resolve simple workflow query type`() {
    assertEquals("query1", workflowQueryType(Workflow1::query1))
  }

  @Test
  fun `workflowQueryType should resolve workflow query type override`() {
    assertEquals("customQueryType", workflowQueryType(Workflow1::query2))
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowQueryType should fail if provided with signal method instead of query method`() {
    workflowQueryType(Workflow1::signal1)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowQueryType should fail if used with non-workflow method`() {
    workflowQueryType(NotAWorkflow::aMethod)
  }

  @Test(expected = IllegalArgumentException::class)
  fun `workflowQueryType should fail if not provided with a method reference`() {
    workflowQueryType(::String)
  }

  @WorkflowInterface
  interface Workflow1 {

    @WorkflowMethod
    fun someWorkflowMethod()

    @SignalMethod
    fun signal1()

    @SignalMethod(name = "customSignalName")
    fun signal2()

    @UpdateMethod
    fun update1(input: String): Int

    @UpdateValidatorMethod(updateName = "update1")
    fun update1Validator(input: String)

    @UpdateMethod(name = "customUpdateName")
    fun update2(input: Long): String

    @UpdateValidatorMethod(updateName = "customUpdateName")
    fun update2Validator(input: Long)

    @QueryMethod
    fun query1(): Int

    @QueryMethod(name = "customQueryType")
    fun query2(): Long
  }

  abstract class NotAWorkflow {
    abstract fun aMethod()
  }
}
