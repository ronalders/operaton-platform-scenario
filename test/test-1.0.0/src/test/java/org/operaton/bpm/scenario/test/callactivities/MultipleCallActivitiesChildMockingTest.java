package org.operaton.bpm.scenario.test.callactivities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.test.junit5.ProcessEngineExtension;
import org.operaton.bpm.scenario.Scenario;
import org.operaton.bpm.scenario.delegate.MockedCallActivityDelegate;
import org.operaton.bpm.scenario.test.AbstractTest;

import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak
 */
@ExtendWith(ProcessEngineExtension.class)
public class MultipleCallActivitiesChildMockingTest extends AbstractTest {

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/callactivities/MultipleCallActivitiesTest.bpmn"})
  void testCompleteCallActivities() {

    when(scenario.waitsAtMockedCallActivity("CallActivity1")).thenReturn(MockedCallActivityDelegate::complete);

    when(scenario.waitsAtMockedCallActivity("CallActivity2")).thenReturn(MockedCallActivityDelegate::complete);

    when(scenario.waitsAtMockedCallActivity("CallActivity3")).thenReturn(MockedCallActivityDelegate::complete);

    Scenario.run(scenario)
      .withMockedProcess("Child1")
      .withMockedProcess("Child2")
      .startByKey("MultipleCallActivitiesTest")
      .execute();

    verify(scenario, times(1)).hasFinished("CallActivity1");
    verify(scenario, times(1)).hasFinished("CallActivity2");
    verify(scenario, times(1)).hasFinished("CallActivity3");
    verify(scenario, times(1)).hasFinished("EndEvent");

  }

}
