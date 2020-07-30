package com.training.helpdesk.history.domain;

import com.training.helpdesk.user.domain.User;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class represents a history event related to the ticket of the 'Help-Desk'
 * application.
 *
 * @author Alexandr_Terehov
 */
@Entity
@Table(name = "history")
public class History {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "ticket_id")
    private Long ticketId;
    
    @Column(name = "action")
    private String action;
    
    @Column(name = "description")
    private String description;
    
    public History( ) {
    
    }
    
    /**
     * Constructor.
     *
     * @param user
     *            user who made the history event.
     * @param date
     *             date when comment was made.
     * @param action
     *            action of the history event.
     * @param description
     *            description of the history event.                            
     */
    public History(final User user, final Timestamp date, 
            final String action, final String description) {
        this.user = user;
        this.date = date;
        this.action = action;
        this.description = description;
    }
    
    /**
     * @return id of the history event.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     *            id of the history event to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return user who left the history event.
     */
    public User getUser() {
        return user;
    }
    
    /**
     * 
     * @param user
     *            user who left the history event to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * @return date when history event was made.
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *            date of the history event creation to set.
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    /**
     * @return id of the ticket related to the particular history event.
     */
    public Long getTicketId() {
        return ticketId;
    }
    
    /**
     * 
     * @param ticketId
     *            id of the ticket related to the particular history event.
     */
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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
     * @return hashCode of the instance of the History class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((user == null) ? 0 : user.hashCode())
                + ((date == null) ? 0 : date.hashCode())
                + ((ticketId == null) ? 0 : ticketId.hashCode())
                + ((action == null) ? 0 : action.hashCode())
                + ((description == null) ? 0 : description.hashCode()));
    }
    
    /**
     * Method used to compare this History to the specified object.
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
        History other = (History) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(user, other.getUser())) {
            return false;
        }
        if (!Objects.equals(date, other.getDate())) {
            return false;
        }
        if (!Objects.equals(ticketId, other.getTicketId())) {
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
     * @return String representation of the object of the History class .
     */
    @Override
    public String toString() {
        return "History [id=" + id + ", userId=" + user.getId() + ", date=" 
                + date + ", ticketId=" + ticketId + ", action="
                + action + ", description=" + description + "]";
    }   
}