package edu.java.bot.model.exception;

import dto.response.ApiErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestController {
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handle(ApiException ex) {

        return new ApiErrorResponse(ex.getMessage(),
            HttpStatus.BAD_REQUEST.toString(),
            ex.getClass().toGenericString(),
            ex.getMessage(),
            getStackTraceAsStringArray(ex));
    }

    public List<String> getStackTraceAsStringArray(Exception ex) {
        List<String> stackTraceString = new ArrayList<>();

        for (StackTraceElement element : ex.getStackTrace()) {
            stackTraceString.add(element.toString() + "\n");
        }
        return stackTraceString;
    }
}
