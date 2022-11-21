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
package org.activiti.runtime.api.event.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import org.activiti.api.process.model.events.BPMNTimerExecutedEvent;
import org.activiti.api.runtime.model.impl.BPMNTimerImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ToTimerExecutedConverterTest {

    @InjectMocks
    private ToTimerExecutedConverter toTimerConverter;

    @Mock
    private BPMNTimerConverter bpmnTimerConverter;

    @Test
    public void shouldReturnConvertedEventsWhenInternalEvenIsRelatedToTimers() {
        //given
        ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
        given(internalEvent.getProcessDefinitionId()).willReturn("procDefId");
        given(internalEvent.getProcessInstanceId()).willReturn("procInstId");

        BPMNTimerImpl bpmnTimer = new BPMNTimerImpl("myTimer");
        given(bpmnTimerConverter.convertToBPMNTimer(internalEvent)).willReturn(bpmnTimer);
        given(bpmnTimerConverter.isTimerRelatedEvent(internalEvent)).willReturn(true);

        //when
        BPMNTimerExecutedEvent timerEvent = toTimerConverter.from(internalEvent).orElse(null);

        //then
        assertThat(timerEvent).isNotNull();
        assertThat(timerEvent.getProcessInstanceId()).isEqualTo("procInstId");
        assertThat(timerEvent.getProcessDefinitionId()).isEqualTo("procDefId");
        assertThat(timerEvent.getEntity()).isEqualTo(bpmnTimer);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenInternalEventIsNotRelatedToTimers() {
        //given
        ActivitiEntityEvent mockActivitiEntityEvent = mock(ActivitiEntityEvent.class);
        given(bpmnTimerConverter.isTimerRelatedEvent(mockActivitiEntityEvent)).willReturn(false);

        //when
        Optional<BPMNTimerExecutedEvent> optional = toTimerConverter.from(mockActivitiEntityEvent);

        //then
        assertThat(optional).isEmpty();
    }
}
