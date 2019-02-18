package org.mybatis.jpetstore.common.user;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.adapter.SessionAdapter;
import com.aspectran.core.component.bean.annotation.AvoidAdvice;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.aware.ActivityContextAware;
import com.aspectran.core.context.ActivityContext;

import java.util.HashMap;

@Component
@Bean("userSessionManager")
@AvoidAdvice
public class UserSessionManager implements ActivityContextAware {

    /**
     * The key used to store user sessions
     */
    public static final String USER_SESSION_KEY = "user";

    private ActivityContext activityContext;

    public void save(UserSession userSession) {
        getSessionAdapter().setAttribute(USER_SESSION_KEY, userSession);
    }

    public void expire() {
        getSessionAdapter().removeAttribute(USER_SESSION_KEY);
    }

    public void checkUserSession() {
        Translet translet = activityContext.getCurrentActivity().getTranslet();
        if (translet == null) {
            throw new UnsupportedOperationException("There is no Translet in " +
                    activityContext.getCurrentActivity());
        }
        UserSession userSession = getUserSession();
        if (userSession == null) {
            translet.redirect("/account/signonForm", new HashMap<String, String>() {{
                put("referrer", translet.getRequestName());
            }});
        }
    }

    public UserSession getUserSession() {
        try {
            return getSessionAdapter().getAttribute(USER_SESSION_KEY);
        } catch (ClassCastException e) {
            // Exception that can occur if the UserSession class changes during development.
            expire();
            return null;
        }
    }

    private SessionAdapter getSessionAdapter() {
        if (activityContext == null) {
            throw new IllegalArgumentException("ActivityContext is not injected");
        }
        SessionAdapter sessionAdapter = activityContext.getCurrentActivity().getSessionAdapter();
        if (sessionAdapter == null) {
            throw new UnsupportedOperationException("There is no SessionAdapter in " +
                    activityContext.getCurrentActivity());
        }
        return sessionAdapter;
    }

    @Override
    public void setActivityContext(ActivityContext activityContext) {
        this.activityContext = activityContext;
    }

}
