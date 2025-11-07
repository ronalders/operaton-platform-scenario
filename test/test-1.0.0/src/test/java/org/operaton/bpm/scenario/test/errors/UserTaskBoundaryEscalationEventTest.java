package org.operaton.bpm.scenario.test.errors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.operaton.bpm.engine.test.Deployment;
import org.operaton.bpm.engine.test.junit5.ProcessEngineExtension;
import org.operaton.bpm.scenario.Scenario;
import org.operaton.bpm.scenario.delegate.TaskDelegate;
import org.operaton.bpm.scenario.test.AbstractTest;

import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak
 */
@ExtendWith(ProcessEngineExtension.class)
public class UserTaskBoundaryEscalationEventTest extends AbstractTest {

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/escalations/UserTaskBoundaryEscalationEventTest.bpmn"})
  void testCompleteTask() {

    when(scenario.waitsAtUserTask("UserTask")).thenReturn(TaskDelegate::complete);

    Scenario.run(scenario).startByKey("UserTaskBoundaryEscalationEventTest").execute();

    verify(scenario, times(1)).hasFinished("UserTask");
    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventInterrupted");
    verify(scenario, never()).hasFinished("EndEventNotInterrupted");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/escalations/UserTaskBoundaryEscalationEventTest.bpmn"})
  void testEscalateNonInterrupting() {

    when(scenario.waitsAtUserTask("UserTask")).thenReturn(callActivity -> {
      callActivity.handleEscalation("escNonInterrupting");
      callActivity.complete();
    });

    Scenario.run(scenario).startByKey("UserTaskBoundaryEscalationEventTest").execute();

    verify(scenario, times(1)).hasFinished("UserTask");
    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventInterrupted");
    verify(scenario, times(1)).hasFinished("EndEventNotInterrupted");

  }

  @Test
  @Deployment(resources = {"org/operaton/bpm/scenario/test/escalations/UserTaskBoundaryEscalationEventTest.bpmn"})
  public void testEscalateInterrupting() {

    when(scenario.waitsAtUserTask("UserTask")).thenReturn(callActivity -> callActivity.handleEscalation("escInterrupting"));

    Scenario.run(scenario).startByKey("UserTaskBoundaryEscalationEventTest").execute();

    verify(scenario, times(1)).hasStarted("UserTask");
    verify(scenario, never()).hasCompleted("UserTask");
    verify(scenario, times(1)).hasCanceled("UserTask");

    verify(scenario, never()).hasFinished("EndEventCompleted");
    verify(scenario, times(1)).hasFinished("EndEventInterrupted");
    verify(scenario, never()).hasFinished("EndEventNotInterrupted");

  }

}
