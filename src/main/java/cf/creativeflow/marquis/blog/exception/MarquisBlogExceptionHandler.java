package cf.creativeflow.marquis.blog.exception;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MarquisBlogExceptionHandler extends ResponseEntityExceptionHandler {


    //todo: add logging (?)

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrorResponse.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage())
            );
        }

        validationErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        validationErrorResponse.setError("Input argument is not valid");
        validationErrorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }


    //todo: unify custom exception handlers (do its more commonly)

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected MarquisExceptionMessage handleArticleNotFoundException(ArticleNotFoundException exception) {

        MarquisExceptionMessage exceptionMessage = new MarquisExceptionMessage();
        exceptionMessage.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionMessage.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        exceptionMessage.setMessage(exception.getMessage());

        return exceptionMessage;
    }

    // Here is shapes of output error messages

    @Data
    class MarquisExceptionMessage {
        int status;
        String error;
        String message;
    }

    @Data
    public class ValidationErrorResponse extends MarquisExceptionMessage {
        private List<Violation> violations = new ArrayList<>();
    }

    @Data
    public class Violation {
        private final String field;
        private final String message;
    }

}
