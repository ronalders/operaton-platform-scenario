package org.operaton.bpm.scenario.impl.job;

import org.operaton.bpm.engine.runtime.Job;
import org.operaton.bpm.scenario.impl.JobExecutable;
import org.operaton.bpm.scenario.impl.ProcessRunnerImpl;

import java.util.Date;

/**
 * @author Martin Schimak
 */
public class ContinuationExecutable extends JobExecutable {

  public ContinuationExecutable(ProcessRunnerImpl runner, Job job) {
    super(runner, job);
  }

  @Override
  public Date isExecutableAt() {
    return new Date(0);
  }

}
