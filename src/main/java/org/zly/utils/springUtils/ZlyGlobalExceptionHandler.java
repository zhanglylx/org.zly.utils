package org.zly.utils.springUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zly.utils.network.ZlyContentType;
import org.zly.utils.result.BaseResult;
import org.zly.utils.result.BaseResultFactoryImpl;

import javax.validation.ValidationException;
import java.util.List;

/**
 * 全局异常处理
 */
@ControllerAdvice
@Component
@Slf4j
public class ZlyGlobalExceptionHandler {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler({ValidationException.class, BindException.class, IllegalArgumentException.class})
    public ResponseEntity<BaseResult<Object>> handleParameterException(Exception exception) {
        String message = exception.getLocalizedMessage();
        if (exception instanceof BindException) {
            BindException binder = (BindException) exception;
            final List<FieldError> fieldErrors = binder.getBindingResult().getFieldErrors();
            if (CollectionUtils.isNotEmpty(fieldErrors)) {
                message = fieldErrors.get(0).getField() + ":" + binder.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
            } else {
                final List<ObjectError> allErrors = binder.getBindingResult().getAllErrors();
//                获取注解上的消息
                if (CollectionUtils.isNotEmpty(allErrors)) {
                    String m = allErrors.get(allErrors.size() - 1).getDefaultMessage();
                    if (StringUtils.isNotBlank(m)) message = m;
                }

            }
        }
        log.error("handleParameterException", exception);
        BaseResultFactoryImpl<Object> testCenterResult = new BaseResultFactoryImpl<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set(ZlyContentType.CONTENT_TYPE, ZlyContentType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(testCenterResult.createErrorParameter(message), headers, HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseResult<Object>> handleException(Exception exception) {
        log.error("handleRuntimeException", exception);
        BaseResultFactoryImpl<Object> testCenterResult = new BaseResultFactoryImpl<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set(ZlyContentType.CONTENT_TYPE, ZlyContentType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(testCenterResult.createErrorException(exception), headers, HttpStatus.OK);
    }
}
