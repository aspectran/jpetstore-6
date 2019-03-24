package com.aspectran.jpetstore.common.filter;

import com.aspectran.core.adapter.RequestAdapter;
import com.aspectran.core.component.bean.annotation.AvoidAdvice;
import com.aspectran.core.component.bean.aware.ActivityContextAware;
import com.aspectran.core.context.ActivityContext;
import com.aspectran.core.util.StringUtils;
import com.aspectran.core.util.apon.AponParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@AvoidAdvice
public class XSSPreventionFilter implements ActivityContextAware {

    private ActivityContext activityContext;

    private Pattern[] patterns;

    @Override
    public void setActivityContext(ActivityContext activityContext) {
        this.activityContext = activityContext;
    }

    public void setPatterns(String patternsText) {
        if (patternsText == null) {
            throw new IllegalArgumentException("patternsText cannot be null");
        }
        XSSPatternHolder xssPatternHolder = new XSSPatternHolder();
        try {
            xssPatternHolder.readFrom(patternsText);
        } catch (AponParseException e) {
            throw new IllegalArgumentException("Patterns parameter can not be parsed", e);
        }
        List<XSSPatternItem> list = xssPatternHolder.getParametersList(XSSPatternHolder.patterns);
        List<Pattern> patterns = new ArrayList<>(list.size());
        for (XSSPatternItem item : list) {
            String patternStr = StringUtils.nullToEmpty(item.getString(XSSPatternItem.pattern));
            boolean caseInsensitive = item.getBoolean(XSSPatternItem.caseInsensitive);
            boolean multiline = item.getBoolean(XSSPatternItem.multiline);
            boolean dotall = item.getBoolean(XSSPatternItem.dotall);
            int flags = 0;
            if (caseInsensitive) {
                flags |= Pattern.CASE_INSENSITIVE;
            }
            if (multiline) {
                flags |= Pattern.MULTILINE;
            }
            if (dotall) {
                flags |= Pattern.DOTALL;
            }
            Pattern pattern = Pattern.compile(patternStr, flags);
            patterns.add(pattern);
        }
        if (!patterns.isEmpty()) {
            this.patterns = patterns.toArray(new Pattern[0]);
        } else {
            this.patterns = null;
        }
    }

    public void filter() {
        RequestAdapter requestAdapter = activityContext.getCurrentActivity().getRequestAdapter();
        if (requestAdapter != null) {
            if (requestAdapter.hasHeaders()) {
                for (List<String> list : requestAdapter.getHeaderMap().values()) {
                    for (int i = 0; i < list.size(); i++) {
                        list.set(i, stripXSS(list.get(i)));
                    }
                }
            }
            if (requestAdapter.hasParameters()) {
                for (String[] arr : requestAdapter.getParameterMap().values()) {
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = stripXSS(arr[i]);
                    }
                }
            }
        }
    }

    private String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            value = value.replaceAll("\0", "");

            // Remove all sections that match a pattern
            if (patterns != null) {
                for (Pattern scriptPattern : patterns) {
                    value = scriptPattern.matcher(value).replaceAll("");
                }
            }
        }
        return value;
    }

}
