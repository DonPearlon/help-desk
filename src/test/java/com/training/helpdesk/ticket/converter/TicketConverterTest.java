package com.training.helpdesk.ticket.converter;

import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.commons.date.converter.DateConverter;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.domain.urgency.Urgency;
import com.training.helpdesk.ticket.dto.TicketCreationDto;
import com.training.helpdesk.ticket.dto.TicketDescriptionDto;
import com.training.helpdesk.ticket.dto.TicketDto;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.domain.role.Role;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Test suite for the {@link TicketConverter} class.
 *
 * @author Alexandr_Terehov
 */
public class TicketConverterTest {
    private DateConverter dateConverter;
    private TicketConverter ticketConverter;

    private Ticket ticket;
    private TicketCreationDto ticketCreationDto;
	
    private User user1;
    private User user2;
    private User user3;
	
    private Date creationDate;
    private Date desiredResolutionDate;
    
    private LocalDate creationDateLocal;
    private LocalDate desiredResolutionDateLocal;
    
    private Category category;

    @Before
    public void initObjects() {
        dateConverter = new DateConverter();
        ticketConverter = new TicketConverter(dateConverter);
    
        user1 = new User(1L, "firstName1", "lastName1",
                Role.EMPLOYEE.getIndex(), "email1", "pass1");
        user2 = new User(2L, "firstName2", "lastName2",
                Role.MANAGER.getIndex(), "email2", "pass2");
        user3 = new User(3L, "firstName3", "lastName3",
                Role.ENGINEER.getIndex(), "email3", "pass3");
    
        creationDate = Date.valueOf("2020-02-03");
        desiredResolutionDate = Date.valueOf("2020-03-03");
        
        creationDateLocal = creationDate.toLocalDate();
        desiredResolutionDateLocal = desiredResolutionDate.toLocalDate();
    
        category = new Category();
        category.setId(1L);
        category.setName("Category");
        
        ticket = new Ticket("Ticket", "descr", 
                desiredResolutionDate, State.IN_PROGRESS.getIndex(), Urgency.AVERAGE.getIndex());
        ticket.setId(3L);
        ticket.setOwner(user1);
        ticket.setCreatedOn(creationDate);
        ticket.setApprover(user2);
        ticket.setAssignee(user3);
        ticket.setCategory(category);
    
        ticketCreationDto = new TicketCreationDto();
        ticketCreationDto.setOwnerId(1L);
        ticketCreationDto.setStateId(State.DONE.getIndex());
        ticketCreationDto.setCategoryId(1L);
        ticketCreationDto.setName("Ticket1");
        ticketCreationDto.setDescription("descr1");
        ticketCreationDto.setUrgencyId(Urgency.HIGH.getIndex());
        ticketCreationDto.setDesiredResolutionDate(desiredResolutionDateLocal);
    }

    @Test
    public void testToDto() {
        //given
        TicketDto expectedTicketDto = new TicketDto(3L, "Ticket",
                desiredResolutionDateLocal, Urgency.AVERAGE, State.IN_PROGRESS);

        //when
        TicketDto resultTicketDto = ticketConverter.toDto(ticket);

        //then
        assertEquals(expectedTicketDto, resultTicketDto);
    }

    @Test
    public void testToDtoWithDescripiton() {
        //given
        TicketDescriptionDto expectedTicketDescrDto = TicketDescriptionDto .newBuilder()
                .setId(3L)
                .setName("Ticket")
                .setCreationDate(creationDateLocal)
                .setStateId(State.IN_PROGRESS.getIndex())
                .setState(State.IN_PROGRESS.toString())
                .setCategoryId(1L)
                .setCategory("Category")
                .setUrgencyId(Urgency.AVERAGE.getIndex())
                .setUrgency(Urgency.AVERAGE.toString())
                .setDescription("descr")
                .setDesiredResolutionDate(desiredResolutionDateLocal)
                .setOwnerId(1L)
                .setOwner(user1)
                .setApprover(user2)
                .setAssignee(user3)
                .build();

        //when
        TicketDescriptionDto resultTicketDescrDto
            = ticketConverter.toDtoWithDescription(ticket);

        //then
        assertEquals(resultTicketDescrDto, expectedTicketDescrDto);
    }

    @Test
    public void testEnrich() {
        //given
        Ticket expectedTicket = new Ticket("Ticket1", "descr1", desiredResolutionDate,
                State.DONE.getIndex(), Urgency.HIGH.getIndex());
        expectedTicket.setId(3L);
        expectedTicket.setOwner(user1);
        expectedTicket.setCreatedOn(creationDate);
        expectedTicket.setApprover(user2);
        expectedTicket.setAssignee(user3);
        expectedTicket.setCategory(category);
    
        //when
        Ticket resultTicket = ticketConverter.enrich(ticket, ticketCreationDto);
    
        //then
        assertEquals(resultTicket, expectedTicket);
    }
}