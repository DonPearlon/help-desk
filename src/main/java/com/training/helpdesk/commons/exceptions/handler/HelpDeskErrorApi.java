package com.training.helpdesk.commons.exceptions.handler;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Class used for representing information about 'help-desk' app errors.
 *
 * @author Alexandr_Terehov
 */
public class HelpDeskErrorApi {
    private HttpStatus status;
    private String message;
    private Integer code;
    private List<String> errors;
        
    public HelpDeskErrorApi() {
    
    }
    
    /**
     * The constructor of the class.
     *
     * @param status - {@link HttpStatus} of the error.
     * @param message - error message.
     * @param errors - list of the errors.
     */
    public HelpDeskErrorApi(final HttpStatus status,
            final String message, final List<String> errors) {
        this.status = status;
        this.message = message;
        this.code = status.value();
        this.errors = errors;
    }
    
    /**
     * The constructor of the class.
     *
     * @param status - {@link HttpStatus} of the error.
     * @param message - error message.
     * @param error - info string about error.
     */
    public HelpDeskErrorApi(final HttpStatus status, final String message, final String error) {
        this.status = status;
        this.message = message;
        this.code = status.value();
        errors = Collections.singletonList(error);
    }
    
    /**
     * @return {@link HttpStatus} of the error.
     */
    public HttpStatus getStatus() {
        return status;
    }
    
    /**
     * 
     * @param status
     *            status of the error to set.
     */
    public void setStatus(final HttpStatus status) {
        this.status = status;
    }
    
    /**
     * @return error message.
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 
     * @param message
     *            error message to set.
     */
    public void setMessage(final String message) {
        this.message = message;
    }
    
    /**
     * @return code of the error.
     */
    public Integer getCode() {
        return code;
    }
    
    /**
     * 
     * @param code
     *            code of the error to set.
     */
    public void setCode(final Integer code) {
        this.code = code;
    }
    
    /**
     * @return .
     */
    public List<String> getErrors() {
        return errors;
    }
    
    /**
     * 
     * @param errors
     *            list of the errors to set.
     */
    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }
    
    /**
     * 
     * @param error
     *            error message to set.
     */
    public void setError(final String error) {
        errors = Collections.singletonList(error);
    }
}
