package com.project.store.exceptions.server;

import com.project.store.dto.error.ErrorDto;
import com.project.store.exceptions.category.CategoryNotFoundException;
import com.project.store.exceptions.customer.CustomerNotFoundException;
import com.project.store.exceptions.order.OrderNotFoundException;
import com.project.store.exceptions.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception exception) {
        return new ErrorDto("Error has occurred", exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequest(Exception exception) {
        return handleBaseException(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleCostumerNotFoundException(Exception exception) {
        return handleBaseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleProductCategoryNotFoundException(Exception exception) {
        return handleBaseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProductNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleProductNotFoundException(Exception exception) {
        return handleBaseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequestValidation(Exception exception) {
        MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) exception;
        List<ObjectError> allErrors = notValidException.getBindingResult().getAllErrors();

        StringBuilder message = new StringBuilder();
        for (ObjectError objectError : allErrors) {
            message.append(objectError.getDefaultMessage());
        }

        return new ErrorDto("failed.validation.for.request.body", message.toString(),
                HttpStatus.BAD_REQUEST.value());
    }

    private ErrorDto handleBaseException(Exception exception, HttpStatus httpStatus){
        BaseException baseException = (BaseException) exception;

        return new ErrorDto(baseException.getErrorCode(), baseException.getMessage(), httpStatus.value());
    }

}
