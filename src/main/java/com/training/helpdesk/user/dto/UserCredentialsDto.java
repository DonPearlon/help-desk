package com.training.helpdesk.user.dto;

import com.training.helpdesk.user.validator.email.Email;
import com.training.helpdesk.user.validator.password.Password;

import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * DTO used for user authentication.
 *
 * @author Alexandr_Terehov
 */
@Validated
public class UserCredentialsDto {
    private static final String INVALID_CREDENTIALS_MESSAGE 
            = "Please make sure you are using a valid email or password.";
    @Email(message = INVALID_CREDENTIALS_MESSAGE)
    private String email;

    @Password(message = INVALID_CREDENTIALS_MESSAGE)
    private String password;

    public UserCredentialsDto() {

    }
    
    /**
     * @return email of the user.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * 
     * @param email
     *            email of the user to set.
     */
    public void setEmail(final String email) {
        this.email = email;
    }
    
    /**
     * @return password of the user.
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 
     * @param password
     *             password of the user to set.
     */
    public void setPassword(final String password) {
        this.password = password;
    }
    
    /**
     * @return hashCode of the object of the UserCredentialsDto class.
     */
    @Override
    public int hashCode() {
        return (31 * ((email == null) ? 0 : email.hashCode()) 
                + ((password == null) ? 0 : password.hashCode()));
    }
    
    /**
     * Method used to compare this UserCredentialsDto to the specified object.
     *
     * @param obj
     *            object to compare.
     * @return result of the comparison.            
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserCredentialsDto other = (UserCredentialsDto) obj;
        if (!Objects.equals(email, other.getEmail())) {
            return false;
        }
        if (!Objects.equals(password, other.getPassword())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the UserCredentialsDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"  
                + "  \"email\" : \"" + email + "\",\r\n" 
                + "  \"password\" : \"" + password + "\"\r\n" 
                + "}";
    }
}