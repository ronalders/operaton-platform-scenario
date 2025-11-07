package org.operaton.bpm.scenario.test.callactivities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.test.junit5.ProcessEngineExtension;
import org.operaton.bpm.scenario.Scenario;
import org.operaton.bpm.scenario.test.AbstractTest;

import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak
 */
@ExtendWith(ProcessEngineExtension.class)
@Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryErrorEventTest.bpmn"})
public class CallActivityWithBoundaryErrorEventTest extends AbstractTest {

  @Test
  void testCompleteTask() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(externalTask -> externalTask.complete());

    Scenario.run(scenario).withMockedProcess("Child").startByKey("BoundaryErrorEventTest").execute();

    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventError");

  }

  @Test
  void testHandleBpmnError() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(externalTask -> externalTask.handleBpmnError("errorCode"));

    Scenario.run(scenario).withMockedProcess("Child").startByKey("BoundaryErrorEventTest").execute();

    verify(scenario, never()).hasFinished("EndEventCompleted");
    verify(scenario, times(1)).hasFinished("EndEventError");

  }

}
