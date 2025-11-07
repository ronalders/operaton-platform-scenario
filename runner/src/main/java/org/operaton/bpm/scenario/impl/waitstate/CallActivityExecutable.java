package org.operaton.bpm.scenario.impl.waitstate;

import org.operaton.bpm.engine.history.HistoricActivityInstance;
import org.operaton.bpm.engine.runtime.ProcessInstance;
import org.operaton.bpm.scenario.ProcessScenario;
import org.operaton.bpm.scenario.act.Action;
import org.operaton.bpm.scenario.act.MockedCallActivityAction;
import org.operaton.bpm.scenario.delegate.ProcessInstanceDelegate;
import org.operaton.bpm.scenario.impl.MockedProcessRunnerImpl;
import org.operaton.bpm.scenario.impl.ProcessRunnerImpl;
import org.operaton.bpm.scenario.impl.delegate.AbstractProcessInstanceDelegate;

/**
 * @author Martin Schimak
 */
public class CallActivityExecutable extends AbstractProcessInstanceDelegate {

  public CallActivityExecutable(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner, instance);
  }

  @Override
  protected ProcessInstance getDelegate() {
    return getRuntimeService().createProcessInstanceQuery().processInstanceId(historicDelegate.getCalledProcessInstanceId()).singleResult();
  }

  @Override
  protected Action<ProcessInstanceDelegate> action(final ProcessScenario scenario) {
    final ProcessRunnerImpl mocked = (ProcessRunnerImpl) scenario.runsCallActivity(getActivityId());
    final MockedCallActivityAction action = scenario.waitsAtMockedCallActivity(getActivityId());
    final ProcessRunnerImpl runner = mocked != null ? mocked : (action != null ? new MockedProcessRunnerImpl(action) : null);
    if (runner != null) {
      return processInstance -> runner.running((CallActivityExecutable) processInstance);
    }
    return null;
  }

  @Override
  public String getRootProcessInstanceId() {
    return getProcessInstance().getRootProcessInstanceId();
  }
}
