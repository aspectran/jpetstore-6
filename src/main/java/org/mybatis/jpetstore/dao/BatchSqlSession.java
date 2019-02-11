package org.mybatis.jpetstore.dao;

import com.aspectran.core.component.bean.annotation.AvoidAdvice;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.with.mybatis.SqlSessionAgent;

@Component
@Bean("batchSqlSession")
@AvoidAdvice
public class BatchSqlSession extends SqlSessionAgent {

    public BatchSqlSession() {
        super("batchTxAspect");
    }

}
