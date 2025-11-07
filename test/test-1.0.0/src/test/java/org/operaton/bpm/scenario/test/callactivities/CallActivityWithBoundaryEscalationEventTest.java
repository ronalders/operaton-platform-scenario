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
public class CallActivityWithBoundaryEscalationEventTest extends AbstractTest {

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryEscalationEventTest.bpmn"})
  void testCompleteTask() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> callActivity.complete());

    Scenario.run(scenario).withMockedProcess("Child").startByKey("CallActivityWithBoundaryEscalationEventTest").execute();

    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventInterrupted");
    verify(scenario, never()).hasFinished("EndEventNotInterrupted");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryEscalationEventTest.bpmn"})
  void testEscalateNonInterrupting() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> {
      callActivity.handleEscalation("escNonInterrupting");
      callActivity.complete();
    });

    Scenario.run(scenario).withMockedProcess("Child").startByKey("CallActivityWithBoundaryEscalationEventTest").execute();

    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventInterrupted");
    verify(scenario, times(1)).hasFinished("EndEventNotInterrupted");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryEscalationEventTest.bpmn"})
  void testEscalateInterrupting() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> callActivity.handleEscalation("escInterrupting"));

    Scenario.run(scenario).withMockedProcess("Child").startByKey("CallActivityWithBoundaryEscalationEventTest").execute();

    verify(scenario, times(1)).hasStarted("CallActivity");
    verify(scenario, never()).hasCompleted("CallActivity");
    verify(scenario, times(1)).hasCanceled("CallActivity");

    verify(scenario, never()).hasFinished("EndEventCompleted");
    verify(scenario, times(1)).hasFinished("EndEventInterrupted");
    verify(scenario, never()).hasFinished("EndEventNotInterrupted");

  }

}
