package com.langsin.oa.config;


import com.langsin.oa.foundation.BaseException;
import com.langsin.oa.foundation.RestCodeEnum;
import com.langsin.oa.foundation.Result;
import com.langsin.oa.foundation.BaseException;
import com.langsin.oa.foundation.RestCodeEnum;
import com.langsin.oa.foundation.Result;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wyy
 * @date 2019/7/11 17:12
 */

@ControllerAdvice
@ResponseBody
public class BaseExceptionHandlerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandlerAdvice.class);

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return ResponseInfo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result parameterExceptionHandler(MethodArgumentNotValidException e) {
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return getResultByException(e,fieldError.getDefaultMessage());
            }
        }
        return new Result(RestCodeEnum.ERROR.getOrdinal(),"参数错误");
    }
    @ExceptionHandler(IllegalStateException.class)
    public Result base(IllegalStateException exception) {
        Result result = getResultByException(exception, "查询参数不能为空");
        return result;
    }
    @ExceptionHandler(UnauthorizedException.class)
    public Result base(UnauthorizedException exception) {
        Result result = getResultByException(exception, "暂无权限,请联系管理员分配权限");
        return result;
    }
    @ExceptionHandler({TypeMismatchException.class,NumberFormatException.class})
    public Result base(NumberFormatException exception) {
        Result result = getResultByException(exception, "数据格式错误");
        return result;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result base(IllegalArgumentException exception) {
        Result result = getResultByException(exception, exception.getMessage());
        return result;
    }

    @ExceptionHandler(BaseException.class)
    public Result base(BaseException exception) {
        Result result = getResultByException(exception, exception.getMessage());
        return result;
    }

    public Result getResultByException(Exception exception, String message) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw, true));
        logger.error(sw.toString());
        Result result = new Result();
        result.setRestCode(RestCodeEnum.ERROR.getOrdinal());
        result.setRestInfo(message);
        return result;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void access(AccessDeniedException exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw, true));
        logger.error(sw.toString());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void sql(SQLException exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw, true));
        logger.error(sw.toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void exception(Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw, true));
        logger.error(sw.toString());
    }


}
