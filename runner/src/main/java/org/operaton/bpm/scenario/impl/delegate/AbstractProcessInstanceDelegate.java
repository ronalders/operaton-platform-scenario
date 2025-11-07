package org.operaton.bpm.scenario.impl.delegate;

import org.operaton.bpm.engine.history.HistoricActivityInstance;
import org.operaton.bpm.engine.runtime.ProcessInstance;
import org.operaton.bpm.scenario.delegate.ProcessInstanceDelegate;
import org.operaton.bpm.scenario.impl.ProcessRunnerImpl;
import org.operaton.bpm.scenario.impl.WaitstateExecutable;

/**
 * @author Martin Schimak
 */
public abstract class AbstractProcessInstanceDelegate extends WaitstateExecutable<ProcessInstance> implements ProcessInstanceDelegate {

  public AbstractProcessInstanceDelegate(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner, instance);
  }

  public String getProcessDefinitionId() {
    return delegate.getProcessDefinitionId();
  }

  public String getBusinessKey() {
    return delegate.getBusinessKey();
  }

  public String getCaseInstanceId() {
    return delegate.getCaseInstanceId();
  }

  public boolean isSuspended() {
    return delegate.isSuspended();
  }

  public String getId() {
    return delegate.getId();
  }

  public boolean isEnded() {
    return delegate.isEnded();
  }

  public String getProcessInstanceId() {
    return delegate.getProcessInstanceId();
  }

  public String getTenantId() {
    return delegate.getTenantId();
  }
  
  @Override
  public String getProcessDefinitionKey() {
    return delegate.getProcessDefinitionKey();
  }

}
