package com.aspectran.jpetstore.common.validation;

import com.aspectran.core.component.bean.ablility.FactoryBean;
import com.aspectran.core.component.bean.ablility.InitializableBean;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.support.i18n.message.MessageSource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Component
@Bean("validator")
public class ValidatorFactoryBean implements InitializableBean, FactoryBean<Validator> {

    private final MessageSource messageSource;

    private ValidatorFactory factory;

    @Autowired(required = false)
    public ValidatorFactoryBean(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize() {
        factory = Validation.buildDefaultValidatorFactory();
//        if (messageSource != null) {
//            MessageInterpolator messageInterpolator = new ResourceBundleMessageInterpolator(locale->
//                    new MessageSourceResourceBundle(messageSource, locale));
//        }
    }

    @Override
    public Validator getObject() {
        return factory.getValidator();
    }

}
