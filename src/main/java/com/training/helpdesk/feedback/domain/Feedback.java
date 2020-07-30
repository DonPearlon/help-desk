package com.training.helpdesk.feedback.domain;

import com.training.helpdesk.ticket.domain.Ticket;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Class represents a feedback related to the ticket of the 'Help-Desk'
 * application.
 *
 * @author Alexandr_Terehov
 */
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "rate")
    private Integer rate;
    
    @Column(name = "date")
    private Date date;
    
    @Column(name = "text")
    private String text;
    
    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public Feedback() {

    }
    
    /**
     * Constructor.
     *
     * @param userId
     *            id of the user who left feedback.
     * @param rate
     *            evaluation of the engineer's work.            
     * @param text
     *             text of the feedback.
     */
    public Feedback(final Long userId, final Integer rate, final String text) {
        this.userId = userId;
        this.rate = rate;
        this.text = text;
    }
    
    /**
     * @return id of the feedback.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     *            id of the feedback to set.
     */ 
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return id of the user who left feedback.
     */
    public Long getUserId() {
        return userId;
    }
    
    /**
     * 
     * @param userId
     *            id of the user who left feedback to set.
     */ 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    /**
     * @return rate of the engineer's work.
     */
    public Integer getRate() {
        return rate;
    }
    
    /**
     * 
     * @param rate
     *            rate of the engineer's work to set.
     */ 
    public void setRate(Integer rate) {
        this.rate = rate;
    }
    
    /**
     * @return date when feedback was made.
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * 
     * @param date
     *            date when feedback was made to set.
     */ 
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * @return text of the feedback.
     */
    public String getText() {
        return text;
    }
    
    /**
     * 
     * @param text
     *            text of the feedback to set.
     */ 
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * @return ticket related to the feedback.
     */
    public Ticket getTicket() {
        return ticket;
    }
    
    /**
     * 
     * @param ticket
     *            ticket related to the feedback to set.
     */ 
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return hashCode of the instance of the Feedback class.
     */
    @Override
    public int hashCode() {
        return (int) (31 * ((id == null) ? 0 : id.hashCode())
                + ((userId == null) ? 0 : userId.hashCode())
                + ((rate == null) ? 0 : rate.hashCode())
                + ((date == null) ? 0 : date.hashCode())
                + ((text == null) ? 0 : text.hashCode())
                + ((ticket == null) ? 0 : ticket.hashCode()));
    }
    
    /**
     * Method used to compare this Feedback to the specified object.
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
        Feedback other = (Feedback) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(userId, other.getUserId())) {
            return false;
        }
        if (!Objects.equals(rate, other.getRate())) {
            return false;
        }
        if (!Objects.equals(date, other.getDate())) {
            return false;
        }
        if (!Objects.equals(text, other.getText())) {
            return false;
        }
        if (!Objects.equals(ticket, other.getTicket())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the Feedback class .
     */
    @Override
    public String toString() {
        return "Feedback [id=" + id + ", userId=" + userId + ", rate="
                + rate + ", date=" + date + ", text=" + text
                + ", ticket=" + ticket.getName() + "]";
    }
}