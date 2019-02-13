/*
 * Copyright (c) 2008-2019 The Aspectran Project
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
package org.mybatis.jpetstore.common.filter;

import com.aspectran.core.util.apon.AbstractParameters;
import com.aspectran.core.util.apon.ParameterDefinition;
import com.aspectran.core.util.apon.ParameterValueType;

public class XSSPatternItem extends AbstractParameters {

    public static final ParameterDefinition pattern;
    public static final ParameterDefinition caseInsensitive;
    public static final ParameterDefinition multiline;
    public static final ParameterDefinition dotall;

    private static final ParameterDefinition[] parameterDefinitions;

    static {
        pattern = new ParameterDefinition("pattern", ParameterValueType.STRING);
        caseInsensitive = new ParameterDefinition("caseInsensitive", ParameterValueType.BOOLEAN);
        multiline = new ParameterDefinition("multiline", ParameterValueType.BOOLEAN);
        dotall = new ParameterDefinition("dotall", ParameterValueType.BOOLEAN);

        parameterDefinitions = new ParameterDefinition[] {
                pattern,
                caseInsensitive,
                multiline,
                dotall
        };
    }

    public XSSPatternItem() {
        super(parameterDefinitions);
    }

}
