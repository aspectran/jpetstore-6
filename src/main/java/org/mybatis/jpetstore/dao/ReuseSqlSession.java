package org.mybatis.jpetstore.dao;

import com.aspectran.core.component.bean.annotation.AvoidAdvice;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.with.mybatis.SqlSessionAgent;

@Component
@Bean("reuseSqlSession")
@AvoidAdvice
public class ReuseSqlSession extends SqlSessionAgent {

    public ReuseSqlSession() {
        super("reuseTxAspect");
    }

}
