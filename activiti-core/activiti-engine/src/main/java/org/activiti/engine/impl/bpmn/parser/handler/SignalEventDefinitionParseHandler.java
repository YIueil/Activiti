/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.impl.bpmn.parser.handler;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;

/**


 */
public class SignalEventDefinitionParseHandler extends AbstractBpmnParseHandler<SignalEventDefinition> {

    public Class<? extends BaseElement> getHandledType() {
        return SignalEventDefinition.class;
    }

    protected void executeParse(BpmnParse bpmnParse, SignalEventDefinition signalDefinition) {
        Signal signal = null;
        if (bpmnParse.getBpmnModel().containsSignalId(signalDefinition.getSignalRef())) {
            signal = bpmnParse.getBpmnModel().getSignal(signalDefinition.getSignalRef());
        }

        if (bpmnParse.getCurrentFlowElement() instanceof IntermediateCatchEvent) {
            IntermediateCatchEvent intermediateCatchEvent = (IntermediateCatchEvent) bpmnParse.getCurrentFlowElement();
            intermediateCatchEvent.setBehavior(
                bpmnParse
                    .getActivityBehaviorFactory()
                    .createIntermediateCatchSignalEventActivityBehavior(
                        intermediateCatchEvent,
                        signalDefinition,
                        signal
                    )
            );
        } else if (bpmnParse.getCurrentFlowElement() instanceof BoundaryEvent) {
            BoundaryEvent boundaryEvent = (BoundaryEvent) bpmnParse.getCurrentFlowElement();
            boundaryEvent.setBehavior(
                bpmnParse
                    .getActivityBehaviorFactory()
                    .createBoundarySignalEventActivityBehavior(
                        boundaryEvent,
                        signalDefinition,
                        signal,
                        boundaryEvent.isCancelActivity()
                    )
            );
        }
    }
}
