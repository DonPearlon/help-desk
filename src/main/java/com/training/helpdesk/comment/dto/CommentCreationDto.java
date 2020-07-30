package com.training.helpdesk.comment.dto;

import com.training.helpdesk.ticket.validator.field.StringField;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Class represents DTO used for a comment creation.
 *
 * @author Alexandr_Terehov
 */
@Validated
public class CommentCreationDto {
    private Long userId;
    private Long ticketId;

    @NotEmpty(message = "Comment field can't be empty")
    @StringField
    private String text;

    public CommentCreationDto() {

    }

    /**
     * @return id of the user.
     */
    public Long getUserId() {
        return userId;
    }
    
    /**
     * 
     * @param userId
     *            id of the user to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     * @return hashCode of the instance of the CommentCreationDto class.
     */
    @Override
    public int hashCode() {
        return (31 * ((userId == null) ? 0 : userId.hashCode())
                + ((ticketId == null) ? 0 : ticketId.hashCode())
                + ((text == null) ? 0 : text.hashCode()));
    }
    
    /**
     * Method used to compare this CommentCreationDto to the specified object.
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
        CommentCreationDto other = (CommentCreationDto) obj;
        if (!Objects.equals(userId, other.getUserId())) {
            return false;
        }
        if (!Objects.equals(ticketId, other.getTicketId())) {
            return false;
        }
        if (!Objects.equals(text, other.getText())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the CommentCreationDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"userId\" : \"" +  userId + "\",\r\n"
                + "  \"ticketId\" : \"" + ticketId + "\"\r\n" 
                + "  \"text\" : \"" +  text + "\",\r\n"
                + "}";
    }
}