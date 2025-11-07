package org.operaton.bpm.scenario.impl.delegate;

import org.operaton.bpm.engine.form.OperatonFormRef;
import org.operaton.bpm.engine.history.HistoricActivityInstance;
import org.operaton.bpm.engine.task.DelegationState;
import org.operaton.bpm.engine.task.Task;
import org.operaton.bpm.scenario.delegate.TaskDelegate;
import org.operaton.bpm.scenario.impl.ProcessRunnerImpl;
import org.operaton.bpm.scenario.impl.WaitstateExecutable;

import java.util.Date;

/**
 * @author Martin Schimak
 */
public abstract class AbstractTaskDelegate extends WaitstateExecutable<Task> implements TaskDelegate {

  public AbstractTaskDelegate(ProcessRunnerImpl runner, HistoricActivityInstance instance) {
    super(runner, instance);
  }

  public String getId() {
    return delegate.getId();
  }

  public String getName() {
    return delegate.getName();
  }

  public void setName(String name) {
    delegate.setName(name);
  }

  public String getDescription() {
    return delegate.getDescription();
  }

  public void setDescription(String description) {
    delegate.setDescription(description);
  }

  public int getPriority() {
    return delegate.getPriority();
  }

  public void setPriority(int priority) {
    delegate.setPriority(priority);
  }

  public String getOwner() {
    return delegate.getOwner();
  }

  public void setOwner(String owner) {
    delegate.setOwner(owner);
  }

  public String getAssignee() {
    return delegate.getAssignee();
  }

  public void setAssignee(String assignee) {
    delegate.setAssignee(assignee);
  }

  public DelegationState getDelegationState() {
    return delegate.getDelegationState();
  }

  public void setDelegationState(DelegationState delegationState) {
    delegate.setDelegationState(delegationState);
  }

  public String getProcessInstanceId() {
    return delegate.getProcessInstanceId();
  }

  public String getProcessDefinitionId() {
    return delegate.getProcessDefinitionId();
  }

  public String getCaseInstanceId() {
    return delegate.getCaseInstanceId();
  }

  public void setCaseInstanceId(String caseInstanceId) {
    delegate.setCaseInstanceId(caseInstanceId);
  }

  public String getCaseExecutionId() {
    return delegate.getCaseExecutionId();
  }

  public String getCaseDefinitionId() {
    return delegate.getCaseDefinitionId();
  }

  public Date getCreateTime() {
    return delegate.getCreateTime();
  }

  public String getTaskDefinitionKey() {
    return delegate.getTaskDefinitionKey();
  }

  public Date getDueDate() {
    return delegate.getDueDate();
  }

  public void setDueDate(Date dueDate) {
    delegate.setDueDate(dueDate);
  }

  public Date getFollowUpDate() {
    return delegate.getFollowUpDate();
  }

  public void setFollowUpDate(Date dueDate) {
    delegate.setFollowUpDate(dueDate);
  }

  public void delegate(String userId) {
    delegate.delegate(userId);
  }

  public String getParentTaskId() {
    return delegate.getParentTaskId();
  }

  public void setParentTaskId(String parentTaskId) {
    delegate.setParentTaskId(parentTaskId);
  }

  public boolean isSuspended() {
    return delegate.isSuspended();
  }

  public String getFormKey() {
    return delegate.getFormKey();
  }

  public String getTenantId() {
    return delegate.getTenantId();
  }

  public void setTenantId(String tenantId) {
    delegate.setTenantId(tenantId);
  }

  @Override
  public Date getLastUpdated() {
    return delegate.getLastUpdated();
  }

  @Override
  public OperatonFormRef getOperatonFormRef() {
    return delegate.getOperatonFormRef();
  }

  @Override
  public String getTaskState() {
    return delegate.getTaskState();
  }

  @Override
  public void setTaskState(String s) {
    delegate.setTaskState(s);
  }

  @Override
  public boolean hasAttachment() {
    return delegate.hasAttachment();
  }

  @Override
  public boolean hasComment() {
    return delegate.hasComment();
  }

}
