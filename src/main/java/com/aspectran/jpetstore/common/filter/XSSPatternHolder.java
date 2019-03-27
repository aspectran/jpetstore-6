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
package com.aspectran.jpetstore.common.filter;

import com.aspectran.core.util.StringUtils;
import com.aspectran.core.util.apon.AbstractParameters;
import com.aspectran.core.util.apon.AponParseException;
import com.aspectran.core.util.apon.ParameterDefinition;

import java.util.List;

public class XSSPatternHolder extends AbstractParameters {

    private static final ParameterDefinition patterns;

    private static final ParameterDefinition[] parameterDefinitions;

    static {
        patterns = new ParameterDefinition("patterns", XSSPatternItem.class, true, false);

        parameterDefinitions = new ParameterDefinition[] {
                patterns
        };
    }

    public XSSPatternHolder() {
        super(parameterDefinitions);
    }

    public XSSPatternHolder(String text) throws AponParseException {
        this();
        readFrom("patterns: [\n" + StringUtils.trimWhitespace(text) + "\n]");
    }

    public List<XSSPatternItem> getXSSPatternItems() {
        return getParametersList(patterns);
    }

    public XSSPatternHolder addXSSPatternItem(XSSPatternItem xssPatternItem) {
        putValue(patterns, xssPatternItem);
        return this;
    }

}
