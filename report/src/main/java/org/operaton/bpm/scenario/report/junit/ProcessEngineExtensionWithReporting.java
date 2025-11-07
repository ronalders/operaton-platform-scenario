package org.operaton.bpm.scenario.report.junit;


import org.operaton.bpm.engine.test.junit5.ProcessEngineExtension;
import org.operaton.bpm.scenario.report.bpmn.ProcessScenarioTestReportGenerator;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author Martin Schimak
 */
public class ProcessEngineExtensionWithReporting extends ProcessEngineExtension {

  public static ProcessEngineExtension builder() {
    return new ProcessEngineExtensionWithReporting();
  }

  public void afterTestExecution(ExtensionContext context) {
    generateProcessScenarioTestReport(context);
    super.afterEach(context);
  }

  private void generateProcessScenarioTestReport(ExtensionContext context) {
    context.getTestMethod().ifPresent(method -> {
      Package featurePackage = method.getDeclaringClass().getPackage();
      String featurePackageName = featurePackage != null ? featurePackage.getName() : null;
      new ProcessScenarioTestReportGenerator(
        featurePackageName,
        method.getDeclaringClass().getSimpleName(),
        method.getName()
      ).generate(deploymentId);
    });
  }

}
