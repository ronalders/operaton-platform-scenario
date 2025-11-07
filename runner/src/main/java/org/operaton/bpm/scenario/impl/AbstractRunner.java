package org.operaton.bpm.scenario.impl;

import org.operaton.bpm.scenario.run.Runner;

import java.util.List;

/**
 * @author Martin Schimak
 */
public abstract class AbstractRunner implements Runner {

  public abstract List<Executable> next();

}
