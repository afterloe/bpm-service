package cn.cityworks.bpm.demo.routes;

import cn.cityworks.bpm.demo.domain.ResponseDTO;
import cn.cityworks.bpm.demo.exceptions.BasicException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.io.Serializable;

/**
 * create by afterloe on 2017/9/5
 */
@RestController
@ControllerAdvice
@RequestMapping("${server.error.path:${error.path:/error}}")
public class Error implements ErrorController, Serializable {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public ResponseDTO doHandleError() {
        return ResponseDTO.build(null, 404,"not found");
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO handleException(Exception e) {
        e.printStackTrace();
        return ResponseDTO.build(null, 500,"system is busy");
    }

    @ExceptionHandler(ActivitiException.class)
    public ResponseDTO handleActivitiException(Exception e) {
        e.printStackTrace();
        return ResponseDTO.build(null, 500,"bpm process engine find error.");
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseDTO handleResourceAccessException(Exception e) {
        return ResponseDTO.build(null, 500,"callback failed");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDTO handleMethodNotSupportedException(Exception e) {
        return ResponseDTO.build(null, 400, e.getMessage());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseDTO handleServletRequestBindingException(Exception e) {
        return ResponseDTO.build(null, 400, "lack header");
    }

    @ExceptionHandler(ActivitiObjectNotFoundException.class)
    public ResponseDTO handleActivitiObjectNotFoundException(Exception e) {
        return ResponseDTO.build(null, 404, "task not found");
    }

    @ExceptionHandler(IOException.class)
    public ResponseDTO handleIOException(Exception e) {
        e.printStackTrace();
        return ResponseDTO.build(null, 400, e.getMessage());
    }

    @ExceptionHandler(HystrixTimeoutException.class)
    public ResponseDTO handleTimeOutException(Exception e) {
        e.printStackTrace();
        return ResponseDTO.build(null, 408,"failed to connect to remote server");
    }

    @ExceptionHandler(BasicException.class)
    public ResponseDTO handleBasicException(BasicException e) {
        return ResponseDTO.build(null, e.getCode(),e.getMessage());
    }
}
