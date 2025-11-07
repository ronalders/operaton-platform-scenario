package org.operaton.bpm.scenario.delegate;

import org.operaton.bpm.engine.runtime.ProcessInstance;
import org.operaton.bpm.scenario.defer.Deferrable;

/**
 * @author Martin Schimak
 */
public interface ProcessInstanceDelegate extends ProcessInstance, VariablesAwareDelegate, Deferrable {

}
