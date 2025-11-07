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
@Deployment(resources = {"org/operaton/bpm/scenario/test/errors/BoundaryErrorEventTest.bpmn"})
public class UserTaskBoundaryErrorEventTest extends AbstractTest {

  @Test
  void testCompleteTask() {

    when(scenario.waitsAtUserTask("UserTask")).thenReturn(TaskDelegate::complete);

    Scenario.run(scenario).startByKey("BoundaryErrorEventTest").execute();

    verify(scenario, times(1)).hasFinished("EndEventCompleted");
    verify(scenario, never()).hasFinished("EndEventError");

  }

  @Test
  void testHandleBpmnError() {

    when(scenario.waitsAtUserTask("UserTask")).thenReturn(task -> task.handleBpmnError("errorCode"));

    Scenario.run(scenario).startByKey("BoundaryErrorEventTest").execute();

    verify(scenario, never()).hasFinished("EndEventCompleted");
    verify(scenario, times(1)).hasFinished("EndEventError");

  }

}
