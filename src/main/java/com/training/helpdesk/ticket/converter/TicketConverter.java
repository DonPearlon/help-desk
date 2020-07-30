package com.training.helpdesk.ticket.converter;

import com.training.helpdesk.commons.date.converter.DateConverter;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.domain.urgency.Urgency;
import com.training.helpdesk.ticket.dto.TicketCreationDto;
import com.training.helpdesk.ticket.dto.TicketDescriptionDto;
import com.training.helpdesk.ticket.dto.TicketDto;

import org.springframework.stereotype.Component;

/**
 * Class provides conversion operations for instances of the {@link Ticket},
 * {@link TicketDto}, {@link TicketDescriptionDto} and {@link TicketCreationDto}
 * classes.
 *
 * @author Alexandr_Terehov
 */
@Component
public class TicketConverter {
    public final DateConverter dateConverter;
    
    /**
     * The constructor of the class.
     *
     * @param dateConverter - {@link DateConverter}.
     */
    public TicketConverter(final DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }
    
    /**
     * Provides conversion of the instance of the {@link Ticket} class to the
     * instance of the {@link TicketDto} class.
     *
     * @param ticket
     *            instance of the {@link Ticket} class.
     */
    public TicketDto toDto(final Ticket ticket) {
        return new TicketDto(
                ticket.getId(), ticket.getName(),
                dateConverter.toLocalDate(ticket.getDesiredResolutionDate()),
                Urgency.getByIndex(ticket.getUrgencyId()),
                State.getByIndex(ticket.getStateId()));
    }
    
    /**
     * Provides conversion of the instance of the {@link Ticket} class to the
     * instance of the {@link TicketDescriptionDto} class.
     *
     * @param ticket
     *            instance of the {@link Ticket} class.
     */
    public TicketDescriptionDto toDtoWithDescription(final Ticket ticket) {
        return TicketDescriptionDto .newBuilder()
                .setId(ticket.getId())
                .setName(ticket.getName())
                .setCreationDate(ticket.getCreatedOn().toLocalDate())
                .setStateId(ticket.getStateId())
                .setState(State.getByIndex(ticket.getStateId()).toString())
                .setCategoryId(ticket.getCategory().getId())
                .setCategory(ticket.getCategory().getName())
                .setUrgencyId(ticket.getUrgencyId())
                .setUrgency(Urgency.getByIndex(ticket.getUrgencyId()).toString())
                .setDescription(ticket.getDescription())
                .setDesiredResolutionDate(dateConverter
                        .toLocalDate(ticket.getDesiredResolutionDate()))
                .setOwnerId(ticket.getOwner().getId())
                .setOwner(ticket.getOwner())
                .setApprover(ticket.getApprover())
                .setAssignee(ticket.getAssignee())
                .setAttachment(ticket.getAttachment())
                .setFeedbackId(ticket.getFeedback())
                .build();
    }

    /**
     * Provides conversion of the instance of the {@link TicketCreationDto} class to
     * the instance of the {@link Ticket} class.
     *
     * @param ticketCreationDto
     *            instance of the {@link TicketCreationDto} class.
     */
    public Ticket toEntity(final TicketCreationDto ticketCreationDto) {
        return new Ticket(ticketCreationDto.getName(), ticketCreationDto.getDescription(),
                dateConverter.toDate(ticketCreationDto.getDesiredResolutionDate()),
                ticketCreationDto.getStateId(), ticketCreationDto.getUrgencyId());
    }
    
    /**
     * Enriches the instance of the {@link Ticket} from the instance 
     * of the {@link TicketCreationDto} class.
     *
     * @param ticket
     *            instance of the {@link Ticket} class.
     * @param ticketDto
     *            instance of the {@link TicketCreationDto} class.
     */
    public Ticket enrich(final Ticket ticket, final TicketCreationDto ticketDto) {
        ticket.setName(ticketDto.getName());
        ticket.setStateId(ticketDto.getStateId());
        ticket.setUrgencyId(ticketDto.getUrgencyId());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setDesiredResolutionDate(
                dateConverter.toDate(ticketDto.getDesiredResolutionDate()));
        return ticket;
    }
}