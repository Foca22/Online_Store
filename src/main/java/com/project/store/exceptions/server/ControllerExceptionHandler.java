package com.project.store.exceptions.server;

import com.project.store.dto.error.ErrorDto;
import com.project.store.exceptions.customer.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleCostumerNotFoundException(HttpServletRequest request, Exception exception) {
        return new ErrorDto("404 NOT_FOUND", exception.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequestValidation(HttpServletRequest request, Exception exception) {
        MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) exception;
        List<ObjectError> allErrors = notValidException.getBindingResult().getAllErrors();

        StringBuilder message = new StringBuilder();
        for (ObjectError objectError : allErrors) {
            message.append(objectError.getDefaultMessage());
            message.append(";");
        }
        return new ErrorDto("failed.validation.for.request.body", message.toString(),
                HttpStatus.BAD_REQUEST.value());
    }

}
