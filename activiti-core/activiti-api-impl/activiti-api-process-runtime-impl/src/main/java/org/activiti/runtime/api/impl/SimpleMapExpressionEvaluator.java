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
package org.activiti.runtime.api.impl;

import java.util.Map;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;

public class SimpleMapExpressionEvaluator implements ExpressionEvaluator {

    private Map<String, Object> context;

    public SimpleMapExpressionEvaluator(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public Object evaluate(
        Expression expression,
        ExpressionManager expressionManager,
        DelegateInterceptor delegateInterceptor
    ) {
        return expression.getValue(expressionManager, delegateInterceptor, context);
    }
}
