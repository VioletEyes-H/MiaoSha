package com.bula.ms.validator;


import com.bula.ms.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验器---IsMobile
 * 校验是不是手机号
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;//值是否是必须的

    /**
     * 初始化方法
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtil.isMobile(s);
        } else {
            if (StringUtils.isEmpty(s)) {//如果值不是必须的，就判断是否存在
                return true;
            } else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
