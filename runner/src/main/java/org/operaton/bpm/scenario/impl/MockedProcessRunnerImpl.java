package org.operaton.bpm.scenario.impl;

import org.operaton.bpm.engine.form.OperatonFormRef;
import org.operaton.bpm.scenario.ProcessScenario;
import org.operaton.bpm.scenario.act.*;
import org.operaton.bpm.scenario.act.*;
import org.operaton.bpm.scenario.defer.Deferred;
import org.operaton.bpm.scenario.delegate.ProcessInstanceDelegate;
import org.operaton.bpm.scenario.impl.delegate.AbstractMockedCallActivityDelegate;
import org.operaton.bpm.scenario.run.Runner;

import java.util.Date;
import java.util.Map;

/**
 * @author Martin Schimak
 */
public class MockedProcessRunnerImpl extends ProcessRunnerImpl {

  public MockedProcessRunnerImpl(final MockedCallActivityAction action) {

    super(null, new ProcessScenario() {

      @Override
      public UserTaskAction waitsAtUserTask(String activityId) {

        return task -> action.execute(new AbstractMockedCallActivityDelegate(task) {

          @Override
          public Date getLastUpdated() {
            return task.getLastUpdated();
          }

          @Override
          public OperatonFormRef getOperatonFormRef() {
            return task.getOperatonFormRef();
          }

          @Override
          public String getTaskState() {
            return task.getTaskState();
          }

          @Override
          public void setTaskState(String s) {
            task.setTaskState(s);
          }

          @Override
          public boolean hasAttachment() {
            return task.hasAttachment();
          }

          @Override
          public boolean hasComment() {
            return task.hasComment();
          }

          @Override
          public void complete() {
            task.complete();
          }

          @Override
          public void complete(Map<String, Object> variables) {
            task.complete(variables);
          }

          @Override
          public Map<String, Object> getVariables() {
            return task.getVariables();
          }

          @Override
          public void handleBpmnError(String errorCode) {
            task.handleBpmnError(errorCode);
          }

          @Override
          public void handleBpmnError(String errorCode, Map<String, Object> variables) {
            task.handleBpmnError(errorCode, variables);
          }

          @Override
          public void handleEscalation(String escalationCode) {
            task.handleEscalation(escalationCode);
          }

          @Override
          public void handleEscalation(String escalationCode, Map<String, Object> variables) {
            task.handleEscalation(escalationCode, variables);
          }

          @Override
          public void defer(String period, Deferred action1) {
            task.defer(period, action1);
          }

          @Override
          public ProcessInstanceDelegate getProcessInstance() {
            return task.getProcessInstance();
          }

        });

      }

      @Override
      public TimerIntermediateEventAction waitsAtTimerIntermediateEvent(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public MessageIntermediateCatchEventAction waitsAtMessageIntermediateCatchEvent(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public ReceiveTaskAction waitsAtReceiveTask(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public SignalIntermediateCatchEventAction waitsAtSignalIntermediateCatchEvent(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public Runner runsCallActivity(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public MockedCallActivityAction waitsAtMockedCallActivity(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public EventBasedGatewayAction waitsAtEventBasedGateway(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public ServiceTaskAction waitsAtServiceTask(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public SendTaskAction waitsAtSendTask(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public MessageIntermediateThrowEventAction waitsAtMessageIntermediateThrowEvent(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public MessageEndEventAction waitsAtMessageEndEvent(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public BusinessRuleTaskAction waitsAtBusinessRuleTask(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public ConditionalIntermediateEventAction waitsAtConditionalIntermediateEvent(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public void hasStarted(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public void hasFinished(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public void hasCompleted(String activityId) {
        throw new IllegalStateException();
      }

      @Override
      public void hasCanceled(String activityId) {
        throw new IllegalStateException();
      }

    });

  }

  @Override
  public void setExecuted() {
    // Overridden in order not to set executed anything for mocked processes
  }

}
