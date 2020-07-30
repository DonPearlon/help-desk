package com.training.helpdesk.comment.domain;

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
 * Class represents a comment related to the ticket of the 'Help-Desk'
 * application.
 *
 * @author Alexandr_Terehov
 */
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "ticket_id")
    private Long ticketId;

    public Comment() {
        
    }
    
    /**
     * Constructor.
     *
     * @param user
     *            user who made the comment.
     * @param text
     *            text of the comment.
     * @param date
     *             date when comment was made.
     */
    public Comment(final User user, final String text, final Timestamp date) {
        this.user = user;
        this.text = text;
        this.date = date;
    }
    
    /**
     * Constructor.
     *
     * @param text
     *            text of the comment.
     * @param ticketId
     *             Id of the ticket related to comment.
     */
    public Comment(final String text, final Long ticketId) {
        this.text = text;
        this.ticketId = ticketId;
    }

    /**
     * @return id of the comment.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     *            id of the comment to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return user who left the comment.
     */
    public User getUser() {
        return user;
    }
    
    /**
     * 
     * @param user
     *            user who left the comment to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * @return text of the comment.
     */
    public String getText() {
        return text;
    }
    
    /**
     * 
     * @param text
     *            text of the comment.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * @return date when comment was made.
     */
    public Timestamp getDate() {
        return date;
    }
    
    /**
     * 
     * @param date
     *            date of the comment creation to set.
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    /**
     * @return id of the ticket related to the particular comment.
     */
    public Long getTicketId() {
        return ticketId;
    }
    
    /**
     * 
     * @param ticketId
     *            id of the ticket related to the particular comment.
     */
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    
    /**
     * @return hashCode of the instance of the Comment class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((user == null) ? 0 : user.hashCode())
                + ((text == null) ? 0 : text.hashCode())
                + ((date == null) ? 0 : date.hashCode())
                + ((ticketId == null) ? 0 : ticketId.hashCode()));
    }
    
    /**
     * Method used to compare this Comment to the specified object.
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
        Comment other = (Comment) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(user, other.getUser())) {
            return false;
        }
        if (!Objects.equals(text, other.getText())) {
            return false;
        }
        if (!Objects.equals(date, other.getDate())) {
            return false;
        }
        if (!Objects.equals(ticketId, other.getTicketId())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the Comment class .
     */
    @Override
    public String toString() {
        return "Comment [id=" + id + ", userId=" + user.getId() + ", "
                + "text=" + text + ", date=" + date + ", ticketId=" + ticketId + "]";
    }
}