package com.training.helpdesk.feedback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.helpdesk.ticket.validator.field.StringField;

import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Class represents DTO of a Feedback related to the ticket of the
 * 'Help-Desk' application.
 *
 * @author Alexandr_Terehov
 */
@Validated
public class FeedbackDto {
    private static final String RATE_ERROR_MESSAGE
            = "Rate must be between 1 and 5.";
    private Long userId;
   
    @Min(value = 1, message = RATE_ERROR_MESSAGE)
    @Max(value = 5, message = RATE_ERROR_MESSAGE)
    @NotNull(message = RATE_ERROR_MESSAGE)
    private Integer rate;
    
    @StringField
    private String text;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    private Long ticketId;
    private String ticketName;

    public FeedbackDto() {

    }
    
    /**
     * Constructor.
     *
     * @param ticketName
     *            name of the ticket related to the feedback.
     * @param rate
     *            evaluation of the engineer's work.            
     * @param text
     *             text of the feedback.
     * @param date
     *             date when feedback was left.
     */
    public FeedbackDto(final String ticketName, final Integer rate, 
            final String text, final LocalDate date) {
        this.ticketName = ticketName;
        this.rate = rate;
        this.text = text;
        this.date = date;
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
     * @return date when feedback was made.
     */
    public LocalDate getDate() {
        return date;
    }
    
    /**
     * 
     * @param date
     *            date when feedback was made to set.
     */ 
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    /**
     * @return id of the ticket related to the feedback.
     */
    public Long getTicketId() {
        return ticketId;
    }
    
    /**
     * 
     * @param ticketId
     *            ticket id to set.
     */ 
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    
    /**
     * @return name of the ticket related to the feedback.
     */
    public String getTicketName() {
        return ticketName;
    }
    
    /**
     * 
     * @param ticketName
     *            ticket name to set.
     */ 
    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    /**
     * @return hashCode of the instance of the FeedbackDto class.
     */
    @Override
    public int hashCode() {
        return (31 * ((userId == null) ? 0 : userId.hashCode())
                + ((rate == null) ? 0 : rate.hashCode())
                + ((date == null) ? 0 : date.hashCode())
                + ((text == null) ? 0 : text.hashCode())
                + ((ticketId == null) ? 0 : ticketId.hashCode())
                + ((ticketName == null) ? 0 : ticketName.hashCode()));
    }
    
    /**
     * Method used to compare this FeedbackDto to the specified object.
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
        FeedbackDto other = (FeedbackDto) obj;
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
        if (!Objects.equals(ticketId, other.getTicketId())) {
            return false;
        }
        if (!Objects.equals(ticketName, other.getTicketName())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the FeedbackDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"userId\" : \"" +  userId + "\",\r\n"
                + "  \"rate\" : \"" +  rate + "\",\r\n"
                + "  \"date\" : \"" +  date + "\",\r\n"
                + "  \"text\" : \"" + text + "\"\r\n" 
                + "  \"ticketId\" : \"" + ticketId + "\"\r\n" 
                + "  \"ticketName\" : \"" + ticketName + "\"\r\n" 
                + "}";
    }
}