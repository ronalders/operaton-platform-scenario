package org.operaton.bpm.scenario.impl.delegate;

import org.operaton.bpm.engine.history.HistoricActivityInstance;
import org.operaton.bpm.engine.runtime.EventSubscription;
import org.operaton.bpm.scenario.delegate.EventSubscriptionDelegate;
import org.operaton.bpm.scenario.impl.ProcessRunnerImpl;
import org.operaton.bpm.scenario.impl.WaitstateExecutable;

import java.util.Date;

/**
 * @author Martin Schimak
 */
public abstract class AbstractEventSubscriptionDelegate extends WaitstateExecutable<EventSubscription> implements EventSubscriptionDelegate {

  public AbstractEventSubscriptionDelegate(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner, instance);
  }

  public String getId() {
    return delegate.getId();
  }

  public String getEventType() {
    return delegate.getEventType();
  }

  public String getEventName() {
    return delegate.getEventName();
  }

  public String getProcessInstanceId() {
    return delegate.getProcessInstanceId();
  }

  public String getTenantId() {
    return delegate.getTenantId();
  }

  public Date getCreated() {
    return delegate.getCreated();
  }

}
