package com.training.helpdesk.ticket.domain;

import com.training.helpdesk.attachment.domain.Attachment;
import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.comment.domain.Comment;
import com.training.helpdesk.feedback.domain.Feedback;
import com.training.helpdesk.history.domain.History;
import com.training.helpdesk.user.domain.User;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Class represents a ticket of the 'Help-Desk' application.
 *
 * @author Alexandr_Terehov
 */
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "desired_resolution_date")
    private Date desiredResolutionDate;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "assignee_id")
    private User assignee;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "state_id")
    private Integer stateId;
    
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "urgency_id")
    private Integer urgencyId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "approver_id")
    private User approver;
        
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments;
        
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "ticket_id")
    private List<History> historyList;
    
    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;
    
    @OneToOne(mappedBy = "ticket", fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Feedback feedback;
      
    public Ticket() {

    }
    
    /**
     * Constructor.
     *
     * @param name
     *            name of the ticket.
     * @param description
     *            description of the ticket.            
     * @param desiredResolutionDate
     *             ticket's desired resolution date.
     * @param stateId
     *             id of the ticket's state.             
     * @param urgencyId
     *             id of the ticket's urgency state.                 
     */
    public Ticket(final String name, final String description,
            final Date desiredResolutionDate, final Integer stateId, 
            final Integer urgencyId) {
        this.name = name;
        this.description = description;
        this.desiredResolutionDate = desiredResolutionDate;
        this.stateId = stateId;
        this.urgencyId = urgencyId;
        comments = new ArrayList<>();
        historyList = new ArrayList<>();
    }
    
    /**
     * @return id of the ticket.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     *            id of the ticket to set.
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return creation date of the ticket.
     */
    public Date getCreatedOn() {
        return createdOn;
    }
    
    /**
     * 
     * @param createdOn
     *            creation date of the ticket to set.
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    /**
     * @return desired resolution date of the ticket.
     */
    public Date getDesiredResolutionDate() {
        return desiredResolutionDate;
    }
    
    /**
     * 
     * @param desiredResolutionDate
     *            desired resolution date of the ticket to set.
     */
    public void setDesiredResolutionDate(Date desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }
    
    /**
     * @return ticket's assignee.
     */
    public User getAssignee() {
        return assignee;
    }
    
    /**
     * 
     * @param assignee
     *            ticket's assignee to set.
     */
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }
    
    /**
     * @return owner of the ticket.
     */
    public User getOwner() {
        return owner;
    }
    
    /**
     * 
     * @param owner
     *            owner of the ticket to set.
     */
    public void setOwner(User owner) {
        this.owner = owner;
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
     *            id of ticket's state to set.
     */
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
    
    /**
     * @return ticket's category.
     */
    public Category getCategory() {
        return category;
    }
    
    /**
     * 
     * @param category
     *              ticket's category to set.
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    
    /**
     * @return id of ticket's urgency state.
     */
    public Integer getUrgencyId() {
        return urgencyId;
    }
    
    /**
     * 
     * @param urgencyId
     *            id of ticket's urgency state to set.
     */
    public void setUrgencyId(Integer urgencyId) {
        this.urgencyId = urgencyId;
    }
    
    /**
     * @return ticket's approver.
     */
    public User getApprover() {
        return approver;
    }
    
    /**
     * 
     * @param approver
     *             ticket's approver to set.
     */
    public void setApprover(User approver) {
        this.approver = approver;
    }
    
    /**
     * @return list of comments related to the ticket.
     */
    public List<Comment> getComments() {
        return comments;
    } 
    
    /**
     * 
     * @param comments
     *            list of comments related to the ticket to set.
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    /**
     * 
     * @param comment
     *            add comment to the ticket.
     */
    public void addComment(Comment comment) {
        comments.add(comment);
    }
    
    /**
     * @return list of history events related to the ticket.
     */
    public List<History> getHistoryList() {
        return historyList;
    } 
    
    /**
     * 
     * @param historyList
     *            list of history events related to the ticket to set.
     */
    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }
    
    /**
     * 
     * @param history
     *            add comment to the ticket.
     */
    public void addHistoryEvent(History history) {
        historyList.add(history);
    }
    
    /**
     * @return attachment related to the ticket.
     */
    public Attachment getAttachment() {
        return attachment;
    }
    
    /**
     * 
     * @param attachment
     *            attachment related to the ticket to set.
     */
    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
            
    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    /**
     * @return hashCode of the instance of the Ticket class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((name == null) ? 0 : name.hashCode())
                + ((description == null) ? 0 : description.hashCode())
                + ((createdOn == null) ? 0 : createdOn.hashCode())
                + ((desiredResolutionDate == null) ? 0 : desiredResolutionDate.hashCode())
                + ((assignee == null) ? 0 : assignee.hashCode())
                + ((owner == null) ? 0 : owner.hashCode())
                + ((stateId == null) ? 0 : stateId.hashCode())
                + ((category == null) ? 0 : category.hashCode())
                + ((urgencyId == null) ? 0 : urgencyId.hashCode())
                + ((approver == null) ? 0 : approver.hashCode()));
    }
    
    /**
     * Method used to compare this Ticket to the specified object.
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
        Ticket other = (Ticket) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }
        if (!Objects.equals(description, other.getDescription())) {
            return false;
        }
        if (!Objects.equals(createdOn, other.getCreatedOn())) {
            return false;
        }
        if (!Objects.equals(desiredResolutionDate, other.getDesiredResolutionDate())) {
            return false;
        }
        if (!Objects.equals(assignee, other.getAssignee())) {
            return false;
        }
        if (!Objects.equals(owner, other.getOwner())) {
            return false;
        }
        if (!Objects.equals(stateId, other.getStateId())) {
            return false;
        }
        if (!Objects.equals(category, other.getCategory())) {
            return false;
        }
        if (!Objects.equals(urgencyId, other.getUrgencyId())) {
            return false;
        }
        if (!Objects.equals(approver, other.getApprover())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the Ticket class .
     */
    @Override
    public String toString() {
        return "Ticket [id=" + id + ", name=" + name + ", description=" + description
                + ", createdOn=" + createdOn + ", desiredResolutionDate=" + desiredResolutionDate
                + ", ownerId=" + owner.getId() + ", stateId=" + stateId
                + ", category=" + category.getName() + ", urgencyId=" + urgencyId + "]";
    }
}