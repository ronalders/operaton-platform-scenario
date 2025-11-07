package org.operaton.bpm.scenario.delegate;

import org.operaton.bpm.engine.runtime.EventSubscription;
import org.operaton.bpm.scenario.defer.Deferrable;

import java.util.Map;

/**
 * @author Martin Schimak
 */
public interface EventSubscriptionDelegate extends EventSubscription, ProcessInstanceAwareDelegate, Deferrable {

  /**
   * Receive the event the event subscription is waiting for.
   */
  void receive();

  /**
   * Receive the event the event subscription is waiting for
   * and deliver a map of received information to be stored
   * as process instance variables.
   */
  void receive(Map<String, Object> variables);

}
