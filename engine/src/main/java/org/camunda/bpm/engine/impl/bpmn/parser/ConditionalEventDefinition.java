/*
 * Copyright 2016 camunda services GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package org.camunda.bpm.engine.impl.bpmn.parser;

import java.io.Serializable;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.impl.Condition;
import org.camunda.bpm.engine.impl.event.EventType;

/**
 * Represents the conditional event definition corresponding to the
 * ConditionalEvent defined by the BPMN 2.0 spec.
 *
 * @author Christopher Zell <christopher.zell@camunda.com>
 */
public class ConditionalEventDefinition extends EventSubscriptionDeclaration implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Expression expression;
  protected Condition conditionalExpression;
  protected boolean interrupting;

  public ConditionalEventDefinition(String eventName, String activityId) {
    super(eventName, EventType.CONDITONAL);
    this.activityId = activityId;
  }

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }

  public Condition getConditionalExpression() {
    return conditionalExpression;
  }

  public void setConditionalExpression(Condition conditionalExpression) {
    this.conditionalExpression = conditionalExpression;
  }

  public boolean isInterrupting() {
    return interrupting;
  }

  public void setInterrupting(boolean interrupting) {
    this.interrupting = interrupting;
  }
}