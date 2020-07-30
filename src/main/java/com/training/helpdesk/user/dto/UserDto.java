package com.training.helpdesk.user.dto;

import com.training.helpdesk.user.domain.role.Role;

import java.util.Objects;

/**
 * Class represents DTO of user of the 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public class UserDto {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Role role;
    private final String email;
       
    /**
     * Constructor.
     *
     * @param id
     *            id of the user.
     * @param firstName
     *            first name of the user.
     * @param lastName
     *            last name of the user.            
     * @param role
     *             role of the user.
     * @param email
     *             email of the user.
     */
    public UserDto(final Long id, final String firstName,
            final String lastName, final Role role, final String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
    }
    
    /**
     * @return id of the user.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @return first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * @return last name of the user.
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * @return role of the user.
     */
    public Role getRole() {
        return role;
    }
    
    /**
     * @return email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return hashCode of the object of the UserDto class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode()) 
                + ((firstName == null) ? 0 : firstName.hashCode())
                + ((lastName == null) ? 0 : lastName.hashCode())
                + ((role == null) ? 0 : role.hashCode())
                + ((email == null) ? 0 : email.hashCode()));
    }
    
    /**
     * Method used to compare this userDto to the specified object.
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
        UserDto other = (UserDto) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(firstName, other.getFirstName())) {
            return false;
        }
        if (!Objects.equals(lastName, other.getLastName())) {
            return false;
        }
        if (role != other.getRole()) {
            return false;
        }
        if (!Objects.equals(email, other.getEmail())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the UserDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"id\" : \"" +  id + "\",\r\n"
                + "  \"firstName\" : \"" +  firstName + "\",\r\n"
                + "  \"lastName\" : \"" +  lastName + "\",\r\n"
                + "  \"role\" : \"" + role + "\",\r\n" 
                + "  \"email\" : \"" + email + "\"\r\n" 
                + "}";
    }
}