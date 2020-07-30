package com.training.helpdesk.ticket.service.impl;

import com.training.helpdesk.attachment.domain.Attachment;
import com.training.helpdesk.attachment.service.AttachmentService;
import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.category.service.CategoryService;
import com.training.helpdesk.commons.date.converter.DateConverter;
import com.training.helpdesk.commons.date.provider.DateTimeProvider;
import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.email.service.EmailService;
import com.training.helpdesk.ticket.action.converter.ActionConverter;
import com.training.helpdesk.ticket.action.domain.Action;
import com.training.helpdesk.ticket.action.dto.ActionDto;
import com.training.helpdesk.ticket.action.service.TicketActionService;
import com.training.helpdesk.ticket.converter.TicketConverter;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.domain.urgency.Urgency;
import com.training.helpdesk.ticket.dto.TicketCreationDto;
import com.training.helpdesk.ticket.dto.TicketDescriptionDto;
import com.training.helpdesk.ticket.dto.TicketDto;
import com.training.helpdesk.ticket.repository.TicketRepository;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.domain.role.Role;
import com.training.helpdesk.user.dto.UserDto;
import com.training.helpdesk.user.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

/**
 * Test suite for the {@link TicketServiceImpl} class.
 *
 * @author Alexandr_Terehov
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {
    @Mock
    private TicketRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private AttachmentService attachmentService;
    @Mock
    private DateTimeProvider dateTimeProvider;
    @Mock
    private TicketActionService ticketActionService; 
    @Mock
    private EmailService emailService;

    private TicketServiceImpl ticketServiceImpl;

    private DateConverter dateConverter;
    private TicketConverter ticketConverter;
    private ActionConverter actionConverter;

    private User user1;
    private User user2;
    private User user3;
    private User user4;

    private UserDto userDto;
    List<UserDto> usersDto;

    private Ticket ticket1;
    private Ticket ticket2;
    private Ticket ticket3;
    private Ticket ticket4;
    
    private List<Ticket> tickets;
    
    private TicketDto ticketDto1;
    private TicketDto ticketDto2;
    private TicketDto ticketDto3;
    
    private List<TicketDto> expectedTicketsDto;
    
    private TicketDescriptionDto expectedTicketDescrDto; 
    private TicketCreationDto ticketCreationDto;
    
    private Date creationDate1;
    private Date creationDate2;
    private Date creationDate3;
    
    private Date desiredResolutionDate1;
    private Date desiredResolutionDate2;
    private Date desiredResolutionDate3;
    
    private LocalDate creationDateLocal1;
    private LocalDate creationDateLocal2;
    private LocalDate creationDateLocal3;
    
    private LocalDate desiredResolutionDateLocal1;
    private LocalDate desiredResolutionDateLocal2;
    private LocalDate desiredResolutionDateLocal3;
    
    private Attachment attachment;
    
    private Category category1;
    
    private Action action1;
    private Action action2;
    private List<Action> actions;
    
    private ActionDto actionDto1;
    private ActionDto actionDto2;
    private List<ActionDto> expectedActionsDto;
    
    @Before
    public void initObjects() {
        user1 = new User(1L, "firstName1", "lastName1",
                Role.EMPLOYEE.getIndex(), "email1", "pass1");
        user2 = new User(2L, "firstName2", "lastName2",
                Role.MANAGER.getIndex(), "email2", "pass2");
        user3 = new User(3L, "firstName3", "lastName3",
                Role.ENGINEER.getIndex(), "email3", "pass3");
        user4 = new User(4L, "firstName4", "lastName4",
                4, "email4", "pass4");
        userDto = new UserDto(4L, "firstName4", "lastName4",
                Role.MANAGER, "email4");
        usersDto = new ArrayList<>();
        usersDto.add(userDto);
    
    
        creationDate1 = Date.valueOf("2020-02-01");
        creationDate2 = Date.valueOf("2020-02-02");
        creationDate3 = Date.valueOf("2020-02-03");
        
        desiredResolutionDate1 = Date.valueOf("2020-03-01");
        desiredResolutionDate2 = Date.valueOf("2020-03-02");
        desiredResolutionDate3 = Date.valueOf("2020-03-03");
        
        creationDateLocal1 = creationDate1.toLocalDate();
        creationDateLocal2 = creationDate2.toLocalDate();
        creationDateLocal3 = creationDate3.toLocalDate();
        
        desiredResolutionDateLocal1 = desiredResolutionDate1.toLocalDate();
        desiredResolutionDateLocal2 = desiredResolutionDate2.toLocalDate();
        desiredResolutionDateLocal3 = desiredResolutionDate3.toLocalDate();
        
        attachment = new Attachment(null, "attachment");
        attachment.setId(1L);
    
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");
    
        dateConverter = new DateConverter();
        ticketConverter = new TicketConverter(dateConverter);
        actionConverter = new ActionConverter();
        ticketServiceImpl = new TicketServiceImpl(
                repository, userService, ticketConverter,
                categoryService, attachmentService, dateTimeProvider,
                actionConverter, ticketActionService, emailService);
    
        ticket1 = new Ticket("Ticket1", "descr1", 
                desiredResolutionDate1, State.DRAFT.getIndex(), Urgency.HIGH.getIndex());
        ticket1.setId(1L);
        ticket1.setOwner(user1);
        ticket1.setCreatedOn(creationDate1);
        ticket1.setCategory(category1);
    
        ticket2 = new Ticket("Ticket2", "descr2", 
                desiredResolutionDate2, State.APPROVED.getIndex(), Urgency.AVERAGE.getIndex());
        ticket2.setId(2L);
        ticket2.setOwner(user1);
        ticket2.setCreatedOn(creationDate2);
        ticket2.setApprover(user2);
        ticket2.setAttachment(attachment);
    
        ticket3 = new Ticket("Ticket3", "descr3", 
                desiredResolutionDate3, State.IN_PROGRESS.getIndex(), Urgency.AVERAGE.getIndex());
        ticket3.setId(3L);
        ticket3.setOwner(user1);
        ticket3.setCreatedOn(creationDate3);
        ticket3.setApprover(user2);
        ticket3.setAssignee(user3);
        ticket3.setCategory(category1);
    
        ticket4 = new Ticket("Ticket1", "descr1", 
                desiredResolutionDate1, State.NEW.getIndex(), Urgency.HIGH.getIndex());
        ticket4.setId(1L);
        ticket4.setOwner(user1);
        ticket4.setCreatedOn(creationDate1);
        ticket4.setCategory(category1);
    
        ticketDto1 = new TicketDto(1L, "Ticket1",
                desiredResolutionDateLocal1, Urgency.HIGH, State.DRAFT);
        ticketDto2 = new TicketDto(2L, "Ticket2",
                desiredResolutionDateLocal2, Urgency.AVERAGE, State.APPROVED);
        ticketDto3 = new TicketDto(3L, "Ticket3",
                desiredResolutionDateLocal3, Urgency.AVERAGE, State.IN_PROGRESS);
    
        tickets = new ArrayList<>();
        tickets.add(ticket1);
        tickets.add(ticket2);
        tickets.add(ticket3);
    
        expectedTicketsDto = new ArrayList<>();
        expectedTicketsDto.add(ticketDto1);
        expectedTicketsDto.add(ticketDto2);
        expectedTicketsDto.add(ticketDto3);
    
        expectedTicketDescrDto = TicketDescriptionDto .newBuilder()
                .setId(3L)
                .setName("Ticket3")
                .setCreationDate(creationDateLocal3)
                .setStateId(State.IN_PROGRESS.getIndex())
                .setState(State.IN_PROGRESS.toString())
                .setCategoryId(1L)
                .setCategory("Category1")
                .setUrgencyId(Urgency.AVERAGE.getIndex())
                .setUrgency(Urgency.AVERAGE.toString())
                .setDescription("descr3")
                .setDesiredResolutionDate(desiredResolutionDateLocal3)
                .setOwnerId(1L)
                .setOwner(user1)
                .setApprover(user2)
                .setAssignee(user3)
                .build();
    
        ticketCreationDto = new TicketCreationDto();
        ticketCreationDto.setOwnerId(1L);
        ticketCreationDto.setStateId(State.DRAFT.getIndex());
        ticketCreationDto.setCategoryId(1L);
        ticketCreationDto.setName("Ticket1");
        ticketCreationDto.setDescription("descr1");
        ticketCreationDto.setUrgencyId(Urgency.HIGH.getIndex());
        ticketCreationDto.setDesiredResolutionDate(desiredResolutionDateLocal1);
    
        action1 = Action.ASSIGN_TO_ME;
        action2 = Action.CANCEL;
    
        actionDto1 = new ActionDto(
                Action.ASSIGN_TO_ME.getIndex(), Action.ASSIGN_TO_ME.toString());
        actionDto2 = new ActionDto(
                Action.CANCEL.getIndex(), Action.CANCEL.toString());
    
        actions = new ArrayList<>();
        actions.add(action1);
        actions.add(action2);
    
        expectedActionsDto = new ArrayList<>();
        expectedActionsDto.add(actionDto1);
        expectedActionsDto.add(actionDto2);
    }
    
    @Test
    public void testGetTicketDtoById() {
        //given:
        Long id = 3L;
        Mockito.when(repository.getTicketById(id))
            .thenReturn(Optional.ofNullable(ticket3));
        
        //when
        TicketDescriptionDto resultTicketDescrDto
            = ticketServiceImpl.getTicketDtoById(id);
        
        //then
        assertEquals(resultTicketDescrDto, expectedTicketDescrDto);
        Mockito.verify(repository, times(1)).getTicketById(id);
    }
    
    @Test(expected = NotFoundException.class)
    public void testGetTicketDtoByIdNotFound() {
        //given:
        Long id = 5L;
        Mockito.when(repository.getTicketById(id))
            .thenReturn(Optional.ofNullable(null));
        
        //when
        ticketServiceImpl.getTicketDtoById(id);
        
        //then
        Mockito.verify(repository, times(1)).getTicketById(id);
    }
    
    @Test
    public void testGetTicketById() {
        //given:
        Long id = 1L;
        Mockito.when(repository.getTicketById(id))
            .thenReturn(Optional.ofNullable(ticket1));
        
        //when
        ticketServiceImpl.getTicketById(id);
        
        //then
        Mockito.verify(repository, times(1)).getTicketById(id);
    }
    
    @Test(expected = NotFoundException.class)
    public void testGetTicketByIdNotFound() {
        //given:
        Long id = 5L;
        Mockito.when(repository.getTicketById(id))
            .thenReturn(Optional.ofNullable(null));
        
        //when
        ticketServiceImpl.getTicketById(id);
        
        //then
        Mockito.verify(repository, times(1)).getTicketById(id);
    }
    
    @Test
    public void testGetTicketsByUserId() {
        //given:
        Long id = 1L;
        Mockito.when(userService.getUserById(id)).thenReturn(user1);
        Mockito.when(repository.getEmployeeTickets(id)).thenReturn(tickets);
           
        //when
        List<TicketDto> resultTicketsDto = ticketServiceImpl.getTicketsByUserId(id);
            
        //then
        assertEquals(resultTicketsDto, expectedTicketsDto);
        Mockito.verify(userService, times(1)).getUserById(id);
        Mockito.verify(repository, times(1)).getEmployeeTickets(id);
    }
    
    @Test(expected = NotFoundException.class)
    public void testGetTicketsByUserIdNotFound() {
        //given:
        Long id = 5L;
        Mockito.when(userService.getUserById(id)).thenReturn(user4);
                   
        //when
        ticketServiceImpl.getTicketsByUserId(id);
            
        //then
        Mockito.verify(userService, times(1)).getUserById(id);
    }
    
    @Test
    public void testGetMyTicketsByUserId() {
        //given:
        Long id = 2L;
        Mockito.when(userService.getUserById(id)).thenReturn(user2);
        Mockito.when(repository.getMyManagerTickets(id)).thenReturn(tickets);
           
        //when
        List<TicketDto> resultTicketsDto = ticketServiceImpl.getMyTicketsByUserId(id);
            
        //then
        assertEquals(resultTicketsDto, expectedTicketsDto);
        Mockito.verify(userService, times(1)).getUserById(id);
        Mockito.verify(repository, times(1)).getMyManagerTickets(id);
    }
    
    @Test(expected = NotFoundException.class)
    public void testGetMyTicketsByUserIdNotFound() {
        //given:
        Long id = 5L;
        Mockito.when(userService.getUserById(id)).thenReturn(user4);
                   
        //when
        ticketServiceImpl.getMyTicketsByUserId(id);
            
        //then
        Mockito.verify(userService, times(1)).getUserById(id);
    }
    
    @Test
    public void testSaveTicket() {
        //given
        Long ownerId = 1L;
        Ticket ticket = ticketConverter.toEntity(ticketCreationDto);
        ticket.setOwner(user1);
        ticket.setCreatedOn(creationDate1);
        ticket.setCategory(category1);
        Mockito.when(userService.getUserById(ownerId)).thenReturn(user1);
        Mockito.when(dateTimeProvider.getCurrentDateUtc()).thenReturn(creationDateLocal1);
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
            .thenReturn(creationDateLocal1.atStartOfDay());
        Mockito.when(categoryService.getCategoryById(1L)).thenReturn(category1);
           	
        //when
        ticketServiceImpl.saveTicket(ownerId, ticketCreationDto, null);
    
        //then
        Mockito.verify(userService, times(1)).getUserById(ownerId);
        Mockito.verify(categoryService, times(1)).getCategoryById(1L);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateUtc();
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(repository, times(1)).insertTicket(ticket);
    }
    
    @Test
    public void testEditTicket() {
        //given
        Long ticketId = 1L;
        Long ownerId = 1L;
        Mockito.when(userService.getUserById(ownerId)).thenReturn(user1);
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(ticket1));
        Ticket ticket = ticketConverter.enrich(ticket1, ticketCreationDto);
        ticket.setCreatedOn(creationDate1);
        ticket.setCategory(category1);
        Mockito.when(dateTimeProvider.getCurrentDateUtc()).thenReturn(creationDateLocal1);
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
            .thenReturn(creationDateLocal2.atStartOfDay());
        Mockito.when(categoryService.getCategoryById(1L)).thenReturn(category1);
    
        //when
        ticketServiceImpl.editTicket(ticketId, ownerId, ticketCreationDto, null);
    
        //then
        Mockito.verify(userService, times(1)).getUserById(ownerId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(categoryService, times(1)).getCategoryById(1L);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateUtc();
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(repository, times(1)).updateTicket(ticket);
    }
    
    @Test(expected = NotFoundException.class)
    public void testEditTicketNeagtive() {
        //given
        Long ticketId = 5L;
        Long ownerId = 2L;
        Mockito.when(userService.getUserById(ownerId)).thenReturn(user1);
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(null));
            
        //when
        ticketServiceImpl.editTicket(ticketId, ownerId, ticketCreationDto, null);
    
        //then
        Mockito.verify(userService, times(1)).getUserById(ownerId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
    }
    
    @Test(expected = ActionForbiddenException.class)
    public void testEditTicketActionForbidden() {
        //given
        Long ticketId = 1L;
        Long ownerId = 2L;
        Mockito.when(userService.getUserById(ownerId)).thenReturn(user2);
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(ticket1));
            
        //when
        ticketServiceImpl.editTicket(ticketId, ownerId, ticketCreationDto, null);
    
        //then
        Mockito.verify(userService, times(1)).getUserById(ownerId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
    }
    
    @Test
    public void testDeleteAttachment() {
        //given
        Long ticketId = 2L;
        Long userId = 1L;
        Long attachmentId = 1L;
        Mockito.when(userService.getUserById(userId)).thenReturn(user1);
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(ticket2));
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
            .thenReturn(creationDateLocal2.atStartOfDay());
               
        //when
        ticketServiceImpl.deleteAttachment(ticketId, userId);
    
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(attachmentService, times(1)).deleteAttachmentById(attachmentId);
    }
    
    @Test(expected = NotFoundException.class)
    public void testDeleteAttachmentNotFound() {
        //given
        Long ticketId = 5L;
        Long userId = 1L;
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(null));
                      
        //when
        ticketServiceImpl.deleteAttachment(ticketId, userId);  
    
        //then
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
    }
    
    @Test(expected = ActionForbiddenException.class)
    public void testDeleteAttachmentActionForbidden() {
        //given
        Long ticketId = 2L;
        Long userId = 2L;
        Mockito.when(userService.getUserById(userId)).thenReturn(user2);
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(ticket2));
                       
        //when
        ticketServiceImpl.deleteAttachment(ticketId, userId);
    
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
    }
    
    @Test
    public void testGetTicketActions() {
        //given
        Long ticketId = 2L;
        Long userId = 3L;
        Mockito.when(userService.getUserById(userId)).thenReturn(user3);
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(ticket2));
        Mockito.when(ticketActionService.getActions(user3, ticket2)).thenReturn(actions);
        
        //when
        List<ActionDto> resultActionsDto 
                = ticketServiceImpl.getTicketActions(userId, ticketId);
        //then
        assertEquals(resultActionsDto, expectedActionsDto);
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(ticketActionService, times(1)).getActions(user3, ticket2);
    }
    
    @Test(expected = NotFoundException.class)
    public void testGetTicketActionsNotFound() {
        //given
        Long ticketId = 5L;
        Long userId = 3L;
        Mockito.when(userService.getUserById(userId)).thenReturn(user3);
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(null));
               
        //when
        ticketServiceImpl.getTicketActions(userId, ticketId);
        
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
    }
    
    @Test
    public void testPerformActionSubmit() throws MessagingException {
        //given
        Long ticketId = 1L;
        Long userId = 1L;
        int actionId = 1;
        Mockito.when(userService.getUserById(userId)).thenReturn(user1);
        Mockito.when(repository.getTicketById(ticketId))
                .thenReturn(Optional.ofNullable(ticket1));
        Mockito.when(ticketActionService
                .checkAction(user1, ticket1, Action.getByIndex(actionId))).thenReturn(true);
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
                .thenReturn(creationDateLocal2.atStartOfDay());
        Mockito.when(userService.getManagers()).thenReturn(usersDto);
        
        //when
        ticketServiceImpl.performAction(userId, ticketId, actionId);
        
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(ticketActionService, times(1))
                .checkAction(user1, ticket1, Action.SUBMIT);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(emailService, times(1))
                .sendTicketNewEmail(userDto.getEmail(), ticket1.getId());
        Mockito.verify(repository, times(1)).updateTicket(ticket1);
    }
    
    @Test
    public void testPerformActionCancel() throws MessagingException {
        //given
        Long ticketId = 2L;
        Long userId = 3L;
        int actionId = 2;
        Mockito.when(userService.getUserById(userId)).thenReturn(user3);
        Mockito.when(repository.getTicketById(ticketId))
                .thenReturn(Optional.ofNullable(ticket2));
        Mockito.when(ticketActionService
                .checkAction(user3, ticket2, Action.getByIndex(actionId))).thenReturn(true);
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
                .thenReturn(creationDateLocal2.atStartOfDay());
                
        //when
        ticketServiceImpl.performAction(userId, ticketId, actionId);
        
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(ticketActionService, times(1))
            .checkAction(user3, ticket2, Action.CANCEL);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(emailService, times(1))
            .sendTicketCancelledEngineerEmail(user1.getEmail(), ticket2.getId());
        Mockito.verify(emailService, times(1))
            .sendTicketCancelledEngineerEmail(user2.getEmail(), ticket2.getId());
        Mockito.verify(repository, times(1)).updateTicket(ticket2);
    }
    
    @Test
    public void testPerformActionApprove() throws MessagingException {
        //given
        Long ticketId = 4L;
        Long userId = 2L;
        int actionId = 3;
        Mockito.when(userService.getUserById(userId)).thenReturn(user2);
        Mockito.when(repository.getTicketById(ticketId))
                .thenReturn(Optional.ofNullable(ticket4));
        Mockito.when(ticketActionService
                .checkAction(user2, ticket4, Action.getByIndex(actionId))).thenReturn(true);
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
                .thenReturn(creationDateLocal2.atStartOfDay());
        Mockito.when(userService.getEngineers()).thenReturn(usersDto);
                
        //when
        ticketServiceImpl.performAction(userId, ticketId, actionId);
        
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(ticketActionService, times(1))
            .checkAction(user2, ticket4, Action.APPROVE);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(emailService, times(1))
            .sendTicketApprovedEmail(user1.getEmail(), ticket4.getId());
        Mockito.verify(emailService, times(1))
            .sendTicketApprovedEmail(userDto.getEmail(), ticket1.getId());
        Mockito.verify(repository, times(1)).updateTicket(ticket4);
    }
    
    @Test
    public void testPerformActionDecline() throws MessagingException {
        //given
        Long ticketId = 4L;
        Long userId = 2L;
        int actionId = 4;
        Mockito.when(userService.getUserById(userId)).thenReturn(user2);
        Mockito.when(repository.getTicketById(ticketId))
                .thenReturn(Optional.ofNullable(ticket4));
        Mockito.when(ticketActionService
                .checkAction(user2, ticket4, Action.getByIndex(actionId))).thenReturn(true);
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
                .thenReturn(creationDateLocal2.atStartOfDay());
                        
        //when
        ticketServiceImpl.performAction(userId, ticketId, actionId);
        
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(ticketActionService, times(1))
            .checkAction(user2, ticket4, Action.DECLINE);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(emailService, times(1))
            .sendTicketDeclinedEmail(user1.getEmail(), user1.getFirstName(),
                    user1.getLastName(), ticket4.getId());
        Mockito.verify(repository, times(1)).updateTicket(ticket4);
    }
    
    @Test
    public void testPerformActionAssign() throws MessagingException {
        //given
        Long ticketId = 2L;
        Long userId = 3L;
        int actionId = 5;
        Mockito.when(userService.getUserById(userId)).thenReturn(user3);
        Mockito.when(repository.getTicketById(ticketId))
                .thenReturn(Optional.ofNullable(ticket2));
        Mockito.when(ticketActionService
                .checkAction(user3, ticket2, Action.getByIndex(actionId))).thenReturn(true);
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
                .thenReturn(creationDateLocal2.atStartOfDay());
                        
        //when
        ticketServiceImpl.performAction(userId, ticketId, actionId);
        
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(ticketActionService, times(1))
            .checkAction(user3, ticket2, Action.ASSIGN_TO_ME);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(repository, times(1)).updateTicket(ticket2);
    }
    
    @Test
    public void testPerformActionDone() throws MessagingException {
        //given
        Long ticketId = 3L;
        Long userId = 3L;
        int actionId = 6;
        Mockito.when(userService.getUserById(userId)).thenReturn(user3);
        Mockito.when(repository.getTicketById(ticketId))
                .thenReturn(Optional.ofNullable(ticket3));
        Mockito.when(ticketActionService
                .checkAction(user3, ticket3, Action.getByIndex(actionId))).thenReturn(true);
        Mockito.when(dateTimeProvider.getCurrentDateTimeUtc())
                .thenReturn(creationDateLocal2.atStartOfDay());
                        
        //when
        ticketServiceImpl.performAction(userId, ticketId, actionId);
        
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
        Mockito.verify(ticketActionService, times(1))
            .checkAction(user3, ticket3, Action.DONE);
        Mockito.verify(dateTimeProvider, times(1)).getCurrentDateTimeUtc();
        Mockito.verify(emailService, times(1))
            .sendTicketDoneEmail(user1.getEmail(), user1.getFirstName(),
                    user1.getLastName(), ticket3.getId());
        Mockito.verify(repository, times(1)).updateTicket(ticket3);
    }
    
    @Test(expected = NotFoundException.class)
    public void testPerformActionNotFound() throws MessagingException {
        //given
        Long ticketId = 5L;
        Long userId = 3L;
        int actionId = 6;
        Mockito.when(userService.getUserById(userId)).thenReturn(user3);
        Mockito.when(repository.getTicketById(ticketId))
            .thenReturn(Optional.ofNullable(null));
                
        //when
        ticketServiceImpl.performAction(userId, ticketId, actionId);
        
        //then
        Mockito.verify(userService, times(1)).getUserById(userId);
        Mockito.verify(repository, times(1)).getTicketById(ticketId);
    }
}