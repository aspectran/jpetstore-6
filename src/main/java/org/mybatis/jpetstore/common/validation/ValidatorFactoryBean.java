package org.mybatis.jpetstore.common.validation;

import com.aspectran.core.component.bean.ablility.FactoryBean;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.context.rule.type.ScopeType;
import com.aspectran.core.support.i18n.message.MessageSource;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * <p>Created: 2019-03-07</p>
 */
@Component
@Bean(id = "validator", scope = ScopeType.SINGLETON, lazyInit = true)
public class ValidatorFactoryBean implements FactoryBean {

    private final MessageSource messageSource;

    @Autowired
    public ValidatorFactoryBean(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Object getObject() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

}
