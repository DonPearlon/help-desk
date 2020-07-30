package com.training.helpdesk.attachment.domain;

import java.util.Objects;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Class represents a attachment related to the ticket of the 'Help-Desk'
 * application.
 *
 * @author Alexandr_Terehov
 */
@Entity
@Table(name = "attachment")
public class Attachment {
    
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "blob", columnDefinition = "BLOB")
    @Lob
    private byte[] blob;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "name")
    private String name;
    
    public Attachment() {

    }
    
    /**
     * Constructor.
     *
     * @param blob
     *            ticket's attachment.
     * @param name
     *            name of the attachment.
     */
    public Attachment(final byte[] blob, final String name) {
        this.blob = blob;
        this.name = name;
    }

    /**
     * @return id of the attachment.
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     *            id of the attachment to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return blob related to the particular attachment.
     */
    public byte[] getBlob() {
        return blob;
    }
    
    /**
     * 
     * @param blob
     *            blob of the attachment to set.
     */
    public void setBlob(byte[] blob) {
        this.blob = blob;
    }
    
    /**
     * @return id of the ticket related to the particular attachment.
     */
    public Long getTicketId() {
        return ticketId;
    }
    
    /**
     * 
     * @param ticketId
     *            id of the ticket related to the particular attachment.
     */
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    
    /**
     * @return name of the attachment.
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     *            name of the attachment.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return hashCode of the instance of the Attachment class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((ticketId == null) ? 0 : ticketId.hashCode())
                + ((name == null) ? 0 : name.hashCode()));
    }
    
    /**
     * Method used to compare this Attachment to the specified object.
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
        Attachment other = (Attachment) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!(blob == other.getBlob())) {
            return false;
        }
        if (!Objects.equals(ticketId, other.getTicketId())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }    
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the Attachment class .
     */
    @Override
    public String toString() {
        return "Attachment [id=" + id + ", ticketId=" + ticketId + ", name=" + name + "]";
    }
}