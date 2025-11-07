package org.operaton.bpm.scenario.test.callactivities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.test.junit5.ProcessEngineExtension;
import org.operaton.bpm.scenario.Scenario;
import org.operaton.bpm.scenario.act.MockedCallActivityAction;
import org.operaton.bpm.scenario.defer.Deferred;
import org.operaton.bpm.scenario.delegate.MockedCallActivityDelegate;
import org.operaton.bpm.scenario.test.AbstractTest;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak
 */
@ExtendWith(ProcessEngineExtension.class)
public class CallActivityWithBoundaryInterruptingTimerTest extends AbstractTest {

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryInterruptingTimerTest.bpmn"})
  void testCompleteTask() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> callActivity.complete());

    Scenario.run(scenario).withMockedProcess("Child").startByKey("BoundaryInterruptingTimerTest").execute();

    verify(scenario, times(1)).waitsAtMockedCallActivity("CallActivity");
    verify(scenario, times(1)).hasStarted("CallActivity");
    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventCanceled");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryInterruptingTimerTest.bpmn"})
  void testExactlyReachingMaximalTimeForTask() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> callActivity.defer("PT5M", new Deferred() {
      @Override
      public void execute() {
        // do nothing
      }
    }));

    Scenario.run(scenario).withMockedProcess("Child").startByKey("BoundaryInterruptingTimerTest").execute();

    verify(scenario, times(1)).waitsAtMockedCallActivity("CallActivity");
    verify(scenario, times(1)).hasStarted("CallActivity");
    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, never()).hasFinished("EndEventCompleted");
    verify(scenario, times(1)).hasFinished("EndEventCanceled");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryInterruptingTimerTest.bpmn"})
  void testTakeMuchTooLongForTask() {

    when(scenario.waitsAtMockedCallActivity("CallActivity"))
      .thenReturn(callActivity -> callActivity.defer("PT6M", new Deferred() {
      @Override
      public void execute() {
        callActivity.complete();
      }
    }));

    Scenario.run(scenario).withMockedProcess("Child").startByKey("BoundaryInterruptingTimerTest").execute();

    verify(scenario, times(1)).hasStarted("CallActivity");
    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, never()).hasFinished("EndEventCompleted");
    verify(scenario, times(1)).hasFinished("EndEventCanceled");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryInterruptingTimerTest.bpmn"})
  void testTakeABitTimeForTask() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(new MockedCallActivityAction() {
      @Override
      public void execute(final MockedCallActivityDelegate callActivity) {
        callActivity.defer("PT4M", () -> callActivity.complete());
      }
    });

    Scenario.run(scenario).withMockedProcess("Child").startByKey("BoundaryInterruptingTimerTest").execute();

    verify(scenario, times(1)).waitsAtMockedCallActivity("CallActivity");
    verify(scenario, times(1)).hasStarted("CallActivity");
    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventCanceled");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryInterruptingTimerTest.bpmn"})
  void testDoNothing() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(new MockedCallActivityAction() {
      @Override
      public void execute(MockedCallActivityDelegate callActivity) {
        // Deal with task but do nothing here
      }
    });

    Scenario.run(scenario).withMockedProcess("Child").startByKey("BoundaryInterruptingTimerTest").execute();

    verify(scenario, times(1)).waitsAtMockedCallActivity("CallActivity");
    verify(scenario, times(1)).hasStarted("CallActivity");
    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, never()).hasFinished("EndEventCompleted");
    verify(scenario, times(1)).hasFinished("EndEventCanceled");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryInterruptingTimerTest.bpmn"})
  void testDoNotDealWithTask() {
    assertThrows(AssertionError.class, () -> Scenario.run(scenario).withMockedProcess("Child").startByKey("BoundaryInterruptingTimerTest").execute());
  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityWithBoundaryInterruptingTimerTest.bpmn"})
  void testWhileOtherProcessInstanceIsRunning() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> callActivity.complete());

    when(otherScenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> callActivity.complete());

    Scenario
      .run(otherScenario).withMockedProcess("Child").startByKey("BoundaryInterruptingTimerTest")
      .run(scenario).withMockedProcess("Child").startByKey("BoundaryInterruptingTimerTest")
      .execute();

    verify(scenario, times(1)).waitsAtMockedCallActivity("CallActivity");
    verify(scenario, times(1)).hasStarted("CallActivity");
    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventCanceled");

  }

}
