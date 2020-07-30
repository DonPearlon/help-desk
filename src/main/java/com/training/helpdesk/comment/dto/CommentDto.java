package com.training.helpdesk.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class represents DTO of a comment related to the ticket of the 'Help-Desk'
 * application.
 *
 * @author Alexandr_Terehov
 */
public class CommentDto {

    private final Long id;
    private final String userName;
    private final String text;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private final LocalDateTime date;

    /**
     * Constructor.
     *
     * @param id
     *            id of the comment.
     * @param userName
     *            name of the user.
     * @param text
     *            text of the comment.
     * @param date
     *             date when comment was made.
     */
    public CommentDto(final Long id, final String userName,
            final String text, final LocalDateTime date) {
        this.id = id;
        this.userName = userName;
        this.text = text;
        this.date = date;
    }

    /**
     * @return id of the ticket.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @return name of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return text of the comment.
     */
    public String getText() {
        return text;
    }
    
    /**
     * @return date when comment was made.
     */
    public LocalDateTime getDate() {
        return date;
    }
    
    /**
     * @return hashCode of the instance of the CommentDto class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((userName == null) ? 0 : userName.hashCode())
                + ((text == null) ? 0 : text.hashCode())
                + ((date == null) ? 0 : date.hashCode()));
    }
    
    /**
     * Method used to compare this CommentDto to the specified object.
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
        CommentDto other = (CommentDto) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(userName, other.getUserName())) {
            return false;
        }
        if (!Objects.equals(text, other.getText())) {
            return false;
        }
        if (!Objects.equals(date, other.getDate())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the CommentDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"id\" : \"" +  id + "\",\r\n"
                + "  \"userName\" : \"" +  userName + "\",\r\n"
                + "  \"text\" : \"" +  text + "\",\r\n"
                + "  \"date\" : \"" +  date + "\",\r\n"
                + "}";
    }
}