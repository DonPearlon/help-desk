package com.training.helpdesk.ticket.dto;

import com.training.helpdesk.ticket.validator.field.StringField;
import com.training.helpdesk.ticket.validator.name.TicketName;

import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * Class represents DTO used for a ticket creation.
 *
 * @author Alexandr_Terehov
 */
@Validated
public class TicketCreationDto {
    private Long ownerId;

    @NotNull(message = "State can't be null")
    private Integer stateId;

    @NotNull(message = "Category can't be null")
    private Long categoryId;

    @TicketName
    private String name;

    @StringField
    private String description;

    @NotNull(message = "Urgency can't be null")
    private Integer urgencyId;

    private LocalDate desiredResolutionDate;
   
    @StringField
    private String comment;
    
    public TicketCreationDto() {

    }

    /**
     * @return id of the ticket's owner.
     */
    public Long getOwnerId() {
        return ownerId;
    }
    
    /**
     * 
     * @param ownerId
     *            id of the ticket's owner to set.
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    
    /**
     * @return id of the ticket's state.
     */
    public Integer getStateId() {
        return stateId;
    }
    
    /**
     * 
     * @param stateId
     *            id of the ticket's state to set.
     */
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
    
    /**
     * @return id of the ticket's category.
     */
    public Long getCategoryId() {
        return categoryId;
    }
    
    /**
     * 
     * @param categoryId
     *            id of the ticket's category to set.
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    /**
     * @return name of the ticket.
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     *            name of the ticket to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return description of the ticket.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 
     * @param description
     *            description of the ticket to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return id of the ticket's urgency.
     */
    public Integer getUrgencyId() {
        return urgencyId;
    }
    
    /**
     * 
     * @param urgencyId
     *            id of the ticket's urgency to set.
     */
    public void setUrgencyId(Integer urgencyId) {
        this.urgencyId = urgencyId;
    }
    
    /**
     * @return desired resolution date of the ticket.
     */
    public LocalDate getDesiredResolutionDate() {
        return desiredResolutionDate;
    }
    
    /**
     * 
     * @param desiredResolutionDate
     *            desired resolution date of the ticket to set.
     */
    public void setDesiredResolutionDate(LocalDate desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }
    
    /**
     * @return comment related to the ticket.
     */
    public String getComment() {
        return comment;
    }
    
    /**
     * 
     * @param comment
     *            comment related to the ticket to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return hashCode of the instance of the TicketCreationDTO class.
     */
    @Override
    public int hashCode() {
        return (31 * ((ownerId == null) ? 0 : ownerId.hashCode())
                + ((name == null) ? 0 : name.hashCode())
                + ((stateId == null) ? 0 : stateId.hashCode())
                + ((categoryId == null) ? 0 : categoryId.hashCode())
                + ((urgencyId == null) ? 0 : urgencyId.hashCode())
                + ((description == null) ? 0 : description.hashCode())
                + ((desiredResolutionDate == null) ? 0 : desiredResolutionDate.hashCode())
                + ((comment == null) ? 0 : comment.hashCode()));
    }
    
    /**
     * Method used to compare this TicketCreationDto to the specified object.
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
        TicketCreationDto other = (TicketCreationDto) obj;
        if (!Objects.equals(ownerId, other.getOwnerId())) {
            return false;
        }
        if (!Objects.equals(stateId, other.getStateId())) {
            return false;
        }
        if (!Objects.equals(categoryId, other.getCategoryId())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }
        if (!Objects.equals(description, other.getDescription())) {
            return false;
        }
        if (!Objects.equals(urgencyId, other.getUrgencyId())) {
            return false;
        }
        if (!Objects.equals(desiredResolutionDate, other.getDesiredResolutionDate())) {
            return false;
        }
        if (!Objects.equals(comment, other.getComment())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the TicketCreationDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"ownerId\" : \"" +  ownerId + "\",\r\n"
                + "  \"stateId\" : \"" +  stateId + "\",\r\n"
                + "  \"category\" : \"" +  categoryId + "\",\r\n"
                + "  \"name\" : \"" +  name + "\",\r\n"
                + "  \"description\" : \"" + description + "\"\r\n" 
                + "  \"urgency\" : \"" + urgencyId + "\"\r\n" 
                + "  \"desiredResolutionDate\" : \"" +  desiredResolutionDate + "\",\r\n"
                + "  \"comment\" : \"" +  comment + "\",\r\n"
                + "}";
    }
}