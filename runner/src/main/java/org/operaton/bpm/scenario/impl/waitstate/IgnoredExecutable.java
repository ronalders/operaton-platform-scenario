package org.operaton.bpm.scenario.impl.waitstate;

import org.operaton.bpm.engine.history.HistoricActivityInstance;
import org.operaton.bpm.scenario.ProcessScenario;
import org.operaton.bpm.scenario.act.Action;
import org.operaton.bpm.scenario.impl.ProcessRunnerImpl;
import org.operaton.bpm.scenario.impl.WaitstateExecutable;

import java.util.Date;

/**
 * @author Martin Schimak
 */
public class IgnoredExecutable extends WaitstateExecutable<Object> {

  public IgnoredExecutable(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner, instance);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void execute() {
    runner.setExecuted(this);
  }

  @Override
  protected Action<IgnoredExecutable> action(ProcessScenario scenario) {
    return null;
  }

  @Override
  protected Object getDelegate() {
    return null;
  }

  @Override
  public Date isExecutableAt() {
    return new Date(Long.MAX_VALUE);
  }

}
