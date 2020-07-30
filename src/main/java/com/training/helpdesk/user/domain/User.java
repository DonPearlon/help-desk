package com.training.helpdesk.user.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class represents a user of the 'Help-Desk' application.
 *
 * @author Alexandr_Terehov
 */
@Entity
@Table(name = "user")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role_id")
    private Integer roleId;
 
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
       
    public User() {

    }
    
    /**
     * Constructor.
     *
     * @param id
     *            id of the user.
     * @param firstName
     *            first name of the user.
     * @param lastName
     *            last name of the user.            
     * @param roleId
     *             role of the user.
     * @param email
     *             email of the user.
     * @param password
     *             password of the user.                                       
     */
    public User(final Long id, final String firstName,
            final String lastName, final Integer roleId,
            final String email, final String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
    }
    
    /**
     * @return id of the user.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     *            id of the user to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * 
     * @param firstName
     *            first name of the user to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @return last name of the user.
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * 
     * @param lastName
     *            last name of the user to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * @return role Id of the user.
     */
    public Integer getRoleId() {
        return roleId;
    }
    
    /**
     * 
     * @param roleId
     *            role Id of the user to set.
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
    public void setEmail(String email) {
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
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return hashCode of the instance of the User class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((firstName == null) ? 0 : firstName.hashCode())
                + ((lastName == null) ? 0 : lastName.hashCode())
                + ((roleId == null) ? 0 : roleId.hashCode())
                + ((email == null) ? 0 : email.hashCode())
                + ((password == null) ? 0 : password.hashCode()));
    }
    
    /**
     * Method used to compare this User to the specified object.
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
        User other = (User) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(firstName, other.getFirstName())) {
            return false;
        }
        if (!Objects.equals(lastName, other.getLastName())) {
            return false;
        }
        if (!roleId.equals(other.getRoleId())) {
            return false;
        }
        if (!Objects.equals(email, other.getEmail())) {
            return false;
        }
        if (!Objects.equals(password, other.getPassword())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the User class .
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", roleId=" + roleId
                + ", email=" + email + ", password=*****" + "]";
    }
}