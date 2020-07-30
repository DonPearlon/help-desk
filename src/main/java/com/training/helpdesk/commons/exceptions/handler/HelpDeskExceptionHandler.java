package com.training.helpdesk.commons.exceptions.handler;

import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.commons.exceptions.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

/**
 * Application exception handler.  
 *
 * @author Alexandr_Terehov
 */
@RestControllerAdvice
public class HelpDeskExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER 
            = LoggerFactory.getLogger(HelpDeskExceptionHandler.class); 

    /**
     * 
     * @param exception
     *            instance of the {@link NotFoundException}.
     * @param request
     *            instance of the {@link WebRequest}.            
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(
                final Exception exception, final WebRequest request) {
        LOGGER.error(exception.getMessage());
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(HttpStatus.NOT_FOUND,
                NotFoundException.DEFAULT_MESSAGE, "requested resource was not found");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    /**
     * 
     * @param exception
     *            instance of the {@link ActionForbiddenException}.
     * @param request
     *            instance of the {@link WebRequest}.            
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(ActionForbiddenException.class)
    public ResponseEntity<Object> handleActionForbiddenException(
                final Exception exception, final WebRequest request) {
        LOGGER.error(exception.getMessage());
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(HttpStatus.BAD_REQUEST,
                ActionForbiddenException.DEFAULT_MESSAGE, "action is forbidden");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    /**
     * 
     * @param exception
     *            instance of the {@link BadCredentialsException}.
     * @param request
     *            instance of the {@link WebRequest}.            
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(
            final Exception exception, final WebRequest request) {
        LOGGER.error("Authentication error : ".concat(exception.getMessage()));
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(
                HttpStatus.UNAUTHORIZED, exception.getLocalizedMessage(), "Bad credentials");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
        
    /**
     * 
     * @param exception
     *            instance of the {@link InternalAuthenticationServiceException}.
     * @param request
     *            instance of the {@link WebRequest}.            
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResponseEntity<Object> handleAuthenticationServiceException(
            final Exception exception, final WebRequest request) {
        LOGGER.error("Authentication error : ".concat(exception.getMessage()));
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(
                HttpStatus.UNAUTHORIZED, exception.getLocalizedMessage(), "Bad credentials");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    /**
     * 
     * @param ex
     *            instance of the {@link MethodArgumentTypeMismatchException}.
     * @param request
     *            instance of the {@link WebRequest}.             
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        final String error = "Failed to convert input value, ".concat(ex.getName())
                .concat(" should be of type ").concat(ex.getRequiredType().getName());
        LOGGER.error(error);
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(
                HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
        
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName().concat(": ").concat(error.getDefaultMessage()));
        }
        LOGGER.error("Unable to process the request due to invalid syntax.");
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(
                     HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            final NoHandlerFoundException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final String error = "No handler found for '" 
                .concat(ex.getHttpMethod()).concat(" ")
                .concat(ex.getRequestURL()).concat("' ");
        LOGGER.error(error);
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(
                HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));
        LOGGER.error("{} method is not supported.", ex.getMethod());
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    /**
     * Handles unspecified exceptions.
     * 
     * @param ex
     *            instance of the {@link Exception}.
     * @param request
     *            instance of the {@link WebRequest}.             
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        LOGGER.error(ex.getMessage());
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    /**
     * Handles constraint violation exceptions.
     * 
     * @param ex
     *            instance of the {@link ConstraintViolationException}.
     * @param request
     *            instance of the {@link WebRequest}.             
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolation(
                ConstraintViolationException ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        List<String> details = ex.getConstraintViolations().parallelStream().map(
                message -> message.getMessage()).collect(Collectors.toList());
        final HelpDeskErrorApi apiError = new HelpDeskErrorApi(
                HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), details);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}