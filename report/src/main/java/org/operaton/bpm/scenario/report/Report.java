package org.operaton.bpm.scenario.report;

import org.operaton.bpm.model.bpmn.BpmnModelInstance;
import org.operaton.bpm.scenario.report.bpmn.ProcessCoverageReport;
import org.operaton.bpm.scenario.report.bpmn.ProcessScenarioReport;

/**
 * @author Martin Schimak
 */
public interface Report<R> {

  R generate(String id);

  static Report<BpmnModelInstance> processScenarioReport() {
    return new ProcessScenarioReport();
  }

  static Report<BpmnModelInstance> processCoverageReport() {
    return new ProcessCoverageReport();
  }

}
