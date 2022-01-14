package com.langsin.oa.aspect;

import com.langsin.oa.constant.ConstantsPool;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.constant.ConstantsPool;
import com.langsin.oa.foundation.Result;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

/**
 * 验证控制器参数aop
 *
 * @author wyy
 * @date 2019/09/30
 */
@Component
@Aspect
public class ValidationAspect {

    @Pointcut("@annotation(com.langsin.oa.annotation.DoValid)")
    public void pointcut() {
    }

    /**
     * @param point 切入点
     * @return result
     * @throws Throwable 异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String typeMismatch = "typeMismatch";
        for (Object arg : point.getArgs()) {
            if (arg instanceof BeanPropertyBindingResult) {
                Errors errors = (BindingResult) arg;
                if (errors.hasErrors()) {
                    String code = errors.getFieldError().getCode();
                    return StringUtils.equals(typeMismatch, code) ? ConstantsPool.EXCEPTION_NETWORK_ERROR : Result.failed(errors.getFieldError().getDefaultMessage());
                }
            }
        }
        return point.proceed();
    }
}
