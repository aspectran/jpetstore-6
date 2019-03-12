package com.aspectran.jpetstore.common.validation;

import com.aspectran.core.component.bean.ablility.FactoryBean;
import com.aspectran.core.component.bean.ablility.InitializableBean;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.support.i18n.message.MessageSource;
import com.aspectran.core.support.i18n.message.MessageSourceResourceBundle;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;

@Component
@Bean("validator")
public class ValidatorFactoryBean implements InitializableBean, FactoryBean<Validator> {

    private final MessageSource messageSource;

    private Validator validator;

    @Autowired(required = false)
    public ValidatorFactoryBean(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize() {
        MessageInterpolator messageInterpolator = null;
        if (messageSource != null) {
            messageInterpolator = new ResourceBundleMessageInterpolator(locale->
                    new MessageSourceResourceBundle(messageSource, locale));
        }

        this.validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(messageInterpolator)
                .buildValidatorFactory()
                .getValidator();
    }

    @Override
    public Validator getObject() {
        return validator;
    }

}
