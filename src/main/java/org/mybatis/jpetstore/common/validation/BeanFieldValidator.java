package org.mybatis.jpetstore.common.validation;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Bean("beanFieldValidator")
public class BeanFieldValidator {

    private final Validator validator;

    @Autowired
    public BeanFieldValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> Map<String, String> validate(T model, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(model, groups);
        if (!constraintViolations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation violation : constraintViolations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
        return null;
    }

}
