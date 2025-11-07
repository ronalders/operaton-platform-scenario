package org.operaton.bpm.scenario.test.callactivities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.test.junit5.ProcessEngineExtension;
import org.operaton.bpm.scenario.Scenario;
import org.operaton.bpm.scenario.act.MockedCallActivityAction;
import org.operaton.bpm.scenario.delegate.MockedCallActivityDelegate;
import org.operaton.bpm.scenario.test.AbstractTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak
 */
@ExtendWith(ProcessEngineExtension.class)
public class CallActivityChildMockingTest extends AbstractTest {

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/CallActivityTest.bpmn"})
  void testCompleteCallActivity() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> callActivity.complete());

    Scenario.run(scenario)
      .withMockedProcess("Child")
      .startByKey("CallActivityTest")
      .execute();

    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, times(1)).hasFinished("EndEvent");

  }

  @Test
  @Deployment(resources = {
    "org/operaton/bpm/scenario/test/callactivities/CallActivityTest.bpmn"
  })
  void testDoNothing() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> {
      // Deal with task but do nothing here
    });

    Scenario.run(scenario).withMockedProcess("Child").startByKey("CallActivityTest").execute();

    verify(scenario, times(1)).hasStarted("CallActivity");
    verify(scenario, never()).hasFinished("CallActivity");
    verify(scenario, never()).hasFinished("EndEvent");

  }

  @Test
  @Deployment(resources = {
    "org/operaton/bpm/scenario/test/callactivities/CallActivityTest.bpmn"
  })
  void testDoNotDealWithCallActivity() {
    assertThrows(AssertionError.class, () -> Scenario.run(scenario).withMockedProcess("Child").startByKey("CallActivityTest").execute());

  }

  @Test
  @Deployment(resources = {
    "org/operaton/bpm/scenario/test/callactivities/CallActivityTest.bpmn",
    "org/operaton/bpm/scenario/test/callactivities/Child.bpmn"
  })
  void testDoMockPresentCallActivity() {
    assertThrows(AssertionError.class, () -> {
      when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> {
        // Deal with task but do nothing here
      });

      Scenario.run(scenario).withMockedProcess("Child").startByKey("CallActivityTest").execute();
    });

  }

  @Test
  @Deployment(resources = {
    "org/operaton/bpm/scenario/test/callactivities/CallActivityTest.bpmn"
  })
  void testDoNotMockCallActivity() {
    assertThrows(Exception.class, () -> {
      when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(new MockedCallActivityAction() {
        @Override
        public void execute(MockedCallActivityDelegate callActivity) {
          // Deal with task but do nothing here
        }
      });
      Scenario.run(scenario).startByKey("CallActivityTest").execute();
    });
  }

  @Test
  @Deployment(resources = {
    "org/operaton/bpm/scenario/test/callactivities/CallActivityTest.bpmn"
  })
  void testWhileOtherProcessInstanceIsRunning() {

    when(scenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> callActivity.complete());

    when(otherScenario.waitsAtMockedCallActivity("CallActivity")).thenReturn(callActivity -> {
    });

    Scenario
      .run(scenario).withMockedProcess("Child").startByKey("CallActivityTest")
      .run(otherScenario).withMockedProcess("Child").startByKey("CallActivityTest")
      .execute();

    verify(scenario, times(1)).hasFinished("CallActivity");
    verify(scenario, times(1)).hasFinished("EndEvent");
    verify(otherScenario, times(1)).hasStarted("CallActivity");
    verify(otherScenario, never()).hasFinished("CallActivity");

  }

}
