package org.mybatis.jpetstore.dao;

import com.aspectran.core.component.bean.annotation.AvoidAdvice;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.with.mybatis.SqlSessionAgent;

@Component
@Bean("simpleSqlSession")
@AvoidAdvice
public class SimpleSqlSession extends SqlSessionAgent {

    public SimpleSqlSession() {
        super("simpleTxAspect");
    }

}
