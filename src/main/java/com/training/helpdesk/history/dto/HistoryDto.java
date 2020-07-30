package com.training.helpdesk.history.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class represents DTO of a history event related to the ticket of the
 * 'Help-Desk' application.
 *
 * @author Alexandr_Terehov
 */
public class HistoryDto {
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;
    private String userName;
    private String action;
    private String description;
    
    /**
     * Constructor.
     *
     * @param userName
     *            name of the user who made the history event.
     * @param action
     *            action of the history event.
     * @param description
     *            description of the history event.                            
     */
    public HistoryDto(final LocalDateTime date, final String userName,
            final String action, final String description) {
        this.date = date;
        this.userName = userName;
        this.action = action;
        this.description = description;
    }
    
    /**
     * @return date when history event occurred.
     */
    public LocalDateTime getDate() {
        return date;
    }
    
    /**
     * 
     * @param date
     *            date of the history event to set.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * @return name of the user.
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * 
     * @param userName
     *            name of the user to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * @return action of the history event.
     */
    public String getAction() {
        return action;
    }
    
    /**
     * 
     * @param action
     *            action of the history event to set.
     */
    public void setAction(String action) {
        this.action = action;
    }
    
    /**
     * @return description of the history event.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 
     * @param description
     *            description of the history event to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return hashCode of the instance of the HistoryDto class.
     */
    @Override
    public int hashCode() {
        return (31 * ((date == null) ? 0 : date.hashCode())
                + ((userName == null) ? 0 : userName.hashCode())
                + ((action == null) ? 0 : action.hashCode())
                + ((description == null) ? 0 : description.hashCode()));
    }
    
    /**
     * Method used to compare this HistoryDto to the specified object.
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
        HistoryDto other = (HistoryDto) obj;
        if (!Objects.equals(date, other.getDate())) {
            return false;
        }
        if (!Objects.equals(userName, other.getUserName())) {
            return false;
        }
        if (!Objects.equals(action, other.getAction())) {
            return false;
        }
        if (!Objects.equals(description, other.getDescription())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the HistoryDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"userName\" : \"" +  userName + "\",\r\n"
                + "  \"date\" : \"" +  date + "\",\r\n"
                + "  \"action\" : \"" + action + "\"\r\n" 
                + "  \"description\" : \"" + description + "\"\r\n" 
                + "}";
    }
}