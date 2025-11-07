package org.operaton.bpm.scenario.impl.waitstate;


import org.operaton.bpm.engine.history.HistoricActivityInstance;
import org.operaton.bpm.engine.runtime.EventSubscription;
import org.operaton.bpm.scenario.ProcessScenario;
import org.operaton.bpm.scenario.act.Action;
import org.operaton.bpm.scenario.delegate.EventSubscriptionDelegate;
import org.operaton.bpm.scenario.impl.ProcessRunnerImpl;
import org.operaton.bpm.scenario.impl.delegate.AbstractEventSubscriptionDelegate;
import org.operaton.bpm.scenario.impl.delegate.EventSubscriptionDelegateImpl;

import java.util.Map;

/**
 * @author Martin Schimak
 */
public class SignalIntermediateCatchEventExecutable extends AbstractEventSubscriptionDelegate {

  private EventSubscriptionDelegate eventSubscriptionDelegate;

  public SignalIntermediateCatchEventExecutable(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner, instance);
    eventSubscriptionDelegate = EventSubscriptionDelegateImpl.newInstance(this, delegate);
  }

  @Override
  protected EventSubscription getDelegate() {
    return getRuntimeService().createEventSubscriptionQuery().eventType("signal").activityId(getActivityId()).executionId(getExecutionId()).singleResult();
  }

  @Override
  protected Action<EventSubscriptionDelegate> action(ProcessScenario scenario) {
    return scenario.waitsAtSignalIntermediateCatchEvent(getActivityId());
  }

  @Override
  public void receive() {
    eventSubscriptionDelegate.receive();
  }

  @Override
  public void receive(Map<String, Object> variables) {
    eventSubscriptionDelegate.receive(variables);
  }

}
