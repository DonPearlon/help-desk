package com.training.helpdesk.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.helpdesk.attachment.domain.Attachment;
import com.training.helpdesk.feedback.domain.Feedback;
import com.training.helpdesk.user.domain.User;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO class used for representing full information about the ticket.
 *
 * @author Alexandr_Terehov
 */
public class TicketDescriptionDto {
    private Long id;
    private String name;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    
    private Integer stateId;
    private String state;
    private Long categoryId;
    private String category;
    private Integer urgencyId;
    private String urgency;
    private String description;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate desiredResolutionDate;
    private String desiredDateStr;
    
    private Long ownerId;
    private String owner;
    private String approver;
    private String assignee;
    
    private Long attachmentId;
    private String attachmentName;
    
    private Long feedbackId;
    
    private TicketDescriptionDto() {
    
    }
    
    /**
     * @return id of the ticket.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @return name of the ticket.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return ticket's creation date.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }
    
    /**
     * @return id of the ticket's state.
     */
    public Integer getStateId() {
        return stateId;
    }
    
    /**
     * @return string representation of the ticket's state.
     */
    public String getState() {
        return state;
    }
    
    /**
     * @return id of the ticket's category.
     */
    public Long getCategoryId() {
        return categoryId;
    }
    
    /**
     * @return name of the ticket's category.
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * @return id of the ticket's urgency state.
     */
    public Integer getUrgencyId() {
        return urgencyId;
    }
    
    /**
     * @return string representation of the ticket's urgency state.
     */
    public String getUrgency() {
        return urgency;
    }
    
    /**
     * @return description of the ticket.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @return desired resolution date of the ticket.
     */
    public LocalDate getDesiredResolutionDate() {
        return desiredResolutionDate;
    }
    
    /**
     * @return desired resolution date of the ticket in String format.
     */
    public String getDesiredDateStr() {
        return desiredDateStr;
    }
    
    /**
     * @return id of the ticket's owner.
     */
    public Long getOwnerId() {
        return ownerId;
    }
    
    /**
     * @return name of the ticket's owner.
     */
    public String getOwner() {
        return owner;
    }
    
    /**
     * @return name of the ticket's approver.
     */
    public String getApprover() {
        return approver;
    }
    
    /**
     * @return name of the ticket's assignee.
     */
    public String getAssignee() {
        return assignee;
    }
    
    /**
     * @return id of attachment related to the ticket.
     */
    public Long getAttachmentId() {
        return attachmentId;
    }
    
    /**
     * @return name of attachment related to the ticket.
     */
    public String getAttachmentName() {
        return attachmentName;
    }
    
    /**
     * @return id of the feedback related to the ticket.
     */
    public Long getFeedbackId() {
        return feedbackId;
    }
    
    /**
     * @return builder of the ticket.
     */
    public static Builder newBuilder() {
        return new TicketDescriptionDto().new Builder();
    }

    /**
     * TicketDescriptionDto builder class.
     */
    public class Builder {

        private Builder() {
            
        }
        
        /**
         * 
         * @param id
         *            id of the ticket to set.
         * @return builder of the ticket.            
         */
        public Builder setId(final Long id) {
            TicketDescriptionDto.this.id = id;
            return this;
        }
        
        /**
         * 
         * @param name
         *            name of the ticket to set.
         * @return builder of the ticket.            
         */
        public Builder setName(final String name) {
            TicketDescriptionDto.this.name = name;
            return this;
        }
        
        /**
         * 
         * @param creationDate
         *            creation date of the ticket to set.
         * @return builder of the ticket.            
         */
        public Builder setCreationDate(final LocalDate creationDate) {
            TicketDescriptionDto.this.creationDate = creationDate;
            return this;
        }
        
        /**
         * 
         * @param stateId
         *            id of the ticket's state to set.
         * @return builder of the ticket.            
         */
        public Builder setStateId(final Integer stateId) {
            TicketDescriptionDto.this.stateId = stateId;
            return this;
        }
        
        /**
         * 
         * @param state
         *            string representation of the ticket's state to set.
         * @return builder of the ticket.            
         */
        public Builder setState(final String state) {
            TicketDescriptionDto.this.state = state;
            return this;
        }
        
        /**
         * 
         * @param categoryId
         *            id of the ticket's category to set.
         * @return builder of the ticket.            
         */
        public Builder setCategoryId(final Long categoryId) {
            TicketDescriptionDto.this.categoryId = categoryId;
            return this;
        }
        
        /**
         * 
         * @param category
         *            category of the ticket to set.
         * @return builder of the ticket.            
         */
        public Builder setCategory(final String category) {
            TicketDescriptionDto.this.category = category;
            return this;
        }
        
        /**
         * 
         * @param urgencyId
         *            id of the ticket's urgency state to set.
         * @return builder of the ticket.            
         */
        public Builder setUrgencyId(final Integer urgencyId) {
            TicketDescriptionDto.this.urgencyId = urgencyId;
            return this;
        }
        
        /**
         * 
         * @param urgency
         *            string representation of the ticket's urgency to set.
         * @return builder of the ticket.            
         */
        public Builder setUrgency(final String urgency) {
            TicketDescriptionDto.this.urgency = urgency;
            return this;
        }
        
        /**
         * 
         * @param description
         *            description of the ticket to set.
         * @return builder of the ticket.            
         */
        public Builder setDescription(final String description) {
            TicketDescriptionDto.this.description = description;
            return this;
        }
        
        /**
         * 
         * @param desiredResolutionDate
         *            desired resolution date of the ticket to set.
         * @return builder of the ticket.            
         */
        public Builder setDesiredResolutionDate(
                final LocalDate desiredResolutionDate) {
            TicketDescriptionDto.this.desiredResolutionDate = desiredResolutionDate;
            if (desiredResolutionDate != null) {
                TicketDescriptionDto.this.desiredDateStr = desiredResolutionDate.toString();
            }
            return this;
        }
        
        /**
         * 
         * @param ownerId
         *            id of the ticket's owner to set.
         * @return builder of the ticket.            
         */
        public Builder setOwnerId(final Long ownerId) {
            TicketDescriptionDto.this.ownerId = ownerId;
            return this;
        }
        
        /**
         * 
         * @param owner
         *            ticket's owner name to set.
         * @return builder of the ticket.            
         */
        public Builder setOwner(final User owner) {
            TicketDescriptionDto.this.owner 
                = owner.getFirstName().concat(" ").concat(owner.getLastName());
            return this;
        }
        
        /**
         * 
         * @param approver
         *            ticket's approver name to set.
         * @return builder of the ticket.            
         */
        public Builder setApprover(final User approver) {
            if (approver != null) {
                TicketDescriptionDto.this.approver 
                    = approver.getFirstName().concat(" ").concat(approver.getLastName());
            }
            return this;
        }
        
        /**
         * 
         * @param assignee
         *            ticket's assignee name to set.
         * @return builder of the ticket.            
         */
        public Builder setAssignee(final User assignee) {
            if (assignee != null) {
                TicketDescriptionDto.this.assignee 
                    = assignee.getFirstName().concat(" ").concat(assignee.getLastName());
            }
            return this;
        }
        
        /**
         * 
         * @param attachment
         *            ticket's attachment to set.
         * @return builder of the ticket.            
         */
        public Builder setAttachment(final Attachment attachment) {
            if (attachment != null) {
                TicketDescriptionDto.this.attachmentId = attachment.getId();
                TicketDescriptionDto.this.attachmentName = attachment.getName();
            }
            return this;
        }
        
        /**
         * 
         * @param feedback
         *            feedback related to the ticket to set.
         * @return builder of the ticket.            
         */
        public Builder setFeedbackId(final Feedback feedback) {
            if (feedback != null) {
                TicketDescriptionDto.this.feedbackId = feedback.getId();
            }
            return this;
        }
        
        /**
         * 
         * @return this TicketDescriptionDto.            
         */
        public TicketDescriptionDto build() {
            return TicketDescriptionDto.this;
        }
    }

    /**
     * @return hashCode of the instance of the TicketDescriptionDTO class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((name == null) ? 0 : name.hashCode())
                + ((creationDate == null) ? 0 : creationDate.hashCode())
                + ((state == null) ? 0 : state.hashCode())
                + ((category == null) ? 0 : category.hashCode())
                + ((urgency == null) ? 0 : urgency.hashCode())
                + ((description == null) ? 0 : description.hashCode())
                + ((desiredResolutionDate == null) ? 0 : desiredResolutionDate.hashCode())
                + ((ownerId == null) ? 0 : ownerId.hashCode())
                + ((owner == null) ? 0 : owner.hashCode())
                + ((approver == null) ? 0 : approver.hashCode())
                + ((assignee == null) ? 0 : assignee.hashCode())
                + ((attachmentId == null) ? 0 : attachmentId.hashCode())
                + ((attachmentName == null) ? 0 : attachmentName.hashCode()));
    }
    
    /**
     * Method used to compare this TicketDescriptionDto to the specified object.
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
        TicketDescriptionDto other = (TicketDescriptionDto) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }
        if (!Objects.equals(creationDate, other.getCreationDate())) {
            return false;
        }
        if (!Objects.equals(state, other.getState())) {
            return false;
        }
        if (!Objects.equals(category, other.getCategory())) {
            return false;
        }
        if (!Objects.equals(urgency, other.getUrgency())) {
            return false;
        }
        if (!Objects.equals(description, other.getDescription())) {
            return false;
        }
        if (!Objects.equals(desiredResolutionDate, other.getDesiredResolutionDate())) {
            return false;
        }
        if (!Objects.equals(ownerId, other.getOwnerId())) {
            return false;
        }
        if (!Objects.equals(owner, other.getOwner())) {
            return false;
        }
        if (!Objects.equals(approver, other.getApprover())) {
            return false;
        }
        if (!Objects.equals(assignee, other.getAssignee())) {
            return false;
        }
        if (!Objects.equals(attachmentId, other.getAttachmentId())) {
            return false;
        }
        if (!Objects.equals(attachmentName, other.getAttachmentName())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
}