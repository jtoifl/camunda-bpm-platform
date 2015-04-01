/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.cmd;

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;

import java.io.Serializable;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.ProcessInstanceModificationBuilderImpl;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.deploy.DeploymentCache;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.runtime.ProcessInstance;


/**
 * @author Tom Baeyens
 * @author Joram Barrez
 */
public class StartProcessInstanceAtActivityCmd implements Command<ProcessInstance>, Serializable {

  private static final long serialVersionUID = 1L;
  protected String processDefinitionKey;
  protected String activityId;

  public StartProcessInstanceAtActivityCmd(String processDefinitionKey, String activityId) {
    this.processDefinitionKey = processDefinitionKey;
    this.activityId = activityId;
  }

  public ProcessInstance execute(CommandContext commandContext) {
    DeploymentCache deploymentCache = Context
      .getProcessEngineConfiguration()
      .getDeploymentCache();

    // Find the process definition
    ProcessDefinitionEntity processDefinition = null;
    processDefinition = deploymentCache.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
    ensureNotNull("No process definition found for key '" + processDefinitionKey + "'", "processDefinition", processDefinition);

    // Start the process instance
    ExecutionEntity processInstance = processDefinition.createProcessInstance();
    processInstance.setActivity(null);

    new ProcessInstanceModificationBuilderImpl(commandContext, processInstance.getId())
      .startBeforeActivity(activityId).execute();

    return processInstance;
  }
}
