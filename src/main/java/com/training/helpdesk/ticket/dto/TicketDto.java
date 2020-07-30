package com.training.helpdesk.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.domain.urgency.Urgency;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class represents DTO of the ticket of the 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public class TicketDto {
    
    private final Long id;
    private final String name;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate desiredResolutionDate;
    private final Urgency urgency;
    private final State state;
    
    /**
     * Constructor.
     *
     * @param id
     *            id of the ticket.
     * @param name
     *            name of the ticket.
     * @param desiredResolutionDate
     *             ticket's desired resolution date.
     *  @param urgency
     *              ticket's urgency state.
     * @param state
     *             ticket's state.             
     */
    public TicketDto(final Long id, final String name, 
            final LocalDate desiredResolutionDate, final Urgency urgency, final State state) {
        this.id = id;
        this.name = name;
        this.desiredResolutionDate = desiredResolutionDate;
        this.urgency = urgency;
        this.state = state;
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
     * @return desired resolution date of the ticket.
     */
    public LocalDate getDesiredResolutionDate() {
        return desiredResolutionDate;
    }
    
    /**
     * @return urgency state of the ticket.
     */
    public Urgency getUrgency() {
        return urgency;
    }
    
    /**
     * @return state of the ticket.
     */
    public State getState() {
        return state;
    }
    
    /**
     * @return hashCode of the instance of the TicketDTO class.
     */
    @Override
    public int hashCode() {
        return (31 * ((id == null) ? 0 : id.hashCode())
                + ((name == null) ? 0 : name.hashCode())
                + ((desiredResolutionDate == null) ? 0 : desiredResolutionDate.hashCode())
                + ((urgency == null) ? 0 : urgency.hashCode())
                + ((state == null) ? 0 : state.hashCode()));
    }
    
    /**
     * Method used to compare this TicketDto to the specified object.
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
        TicketDto other = (TicketDto) obj;
        if (!Objects.equals(id, other.getId())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }
        if (!Objects.equals(desiredResolutionDate, other.getDesiredResolutionDate())) {
            return false;
        }
        if (!Objects.equals(urgency, other.getUrgency())) {
            return false;
        }
        if (!Objects.equals(state, other.getState())) {
            return false;
        }
        return (this.hashCode() == other.hashCode());
    }
    
    /**
     * @return String representation of the object of the TicketDto class .
     */
    @Override
    public String toString() {
        return "{\r\n"
                + "  \"id\" : \"" +  id + "\",\r\n"
                + "  \"desiredResolutionDate\" : \"" +  desiredResolutionDate + "\",\r\n"
                + "  \"urgency\" : \"" +  urgency + "\",\r\n"
                + "  \"state\" : \"" + state + "\"\r\n" 
                + "}";
    }
}