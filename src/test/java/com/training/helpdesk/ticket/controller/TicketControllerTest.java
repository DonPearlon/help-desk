package com.training.helpdesk.ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.commons.configurations.WebConfiguration;
import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.commons.exceptions.handler.HelpDeskExceptionHandler;
import com.training.helpdesk.context.TestContext;
import com.training.helpdesk.ticket.action.domain.Action;
import com.training.helpdesk.ticket.action.dto.ActionDto;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.domain.urgency.Urgency;
import com.training.helpdesk.ticket.dto.TicketCreationDto;
import com.training.helpdesk.ticket.dto.TicketDescriptionDto;
import com.training.helpdesk.ticket.dto.TicketDto;
import com.training.helpdesk.ticket.service.TicketService;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.domain.role.Role;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Test suite for the TicketController class.
 *
 * @author Alexandr_Terehov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebConfiguration.class})
@WebAppConfiguration
public class TicketControllerTest {

    private MockMvc mockMvc;
    
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8"; 

    @Autowired
    @Qualifier("ticketServiceMock")
    private TicketService ticketServiceMock;
    
    private User user1;
    private User user2;
    private User user3;

    private Ticket ticket1;
    private Ticket ticket2;
    private Ticket ticket3;
    private Ticket ticket4;
          
    private TicketDto ticketDto1;
    private TicketDto ticketDto2;
    private TicketDto ticketDto3;
    private TicketDto ticketDto4;
       
    private TicketDescriptionDto ticketDescrDto; 
    private TicketCreationDto ticketCreationDto;
    
    private Date creationDate1;
    private Date creationDate2;
    private Date creationDate3;
    
    private Date desiredResolutionDate1;
    private Date desiredResolutionDate2;
    private Date desiredResolutionDate3;
       
    private LocalDate creationDateLocal;
    
    private LocalDate desiredResolutionDateLocal1;
    private LocalDate desiredResolutionDateLocal2;
    private LocalDate desiredResolutionDateLocal3;
            
    private Category category1;
   
    private ActionDto actionDto1;
    private ActionDto actionDto2;
        
    private ObjectMapper objectMapper;
    
    @Before
    public void setup() {
    
        mockMvc = MockMvcBuilders.standaloneSetup(
                new TicketController(ticketServiceMock))
                    .setControllerAdvice(new HelpDeskExceptionHandler()).build();
    	
        user1 = new User(1L, "firstName1", "lastName1",
                Role.EMPLOYEE.getIndex(), "email1", "pass1");
        user2 = new User(2L, "firstName2", "lastName2",
                Role.MANAGER.getIndex(), "email2", "pass2");
        user3 = new User(3L, "firstName3", "lastName3",
                Role.ENGINEER.getIndex(), "email3", "pass3");
   	
        creationDate1 = Date.valueOf("2020-02-01");
        creationDate2 = Date.valueOf("2020-02-02");
        creationDate3 = Date.valueOf("2020-02-03");
        
        desiredResolutionDate1 = Date.valueOf("2020-03-01");
        desiredResolutionDate2 = Date.valueOf("2020-03-02");
        desiredResolutionDate3 = Date.valueOf("2020-03-03");

        creationDateLocal = creationDate3.toLocalDate();
        
        desiredResolutionDateLocal1 = desiredResolutionDate1.toLocalDate();
        desiredResolutionDateLocal2 = desiredResolutionDate2.toLocalDate();
        desiredResolutionDateLocal3 = desiredResolutionDate3.toLocalDate();
                    	
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");

        ticket1 = new Ticket("ticket1", "descr1", 
                desiredResolutionDate1, State.DRAFT.getIndex(), Urgency.HIGH.getIndex());
        ticket1.setId(1L);
        ticket1.setOwner(user1);
        ticket1.setCreatedOn(creationDate1);
        ticket1.setCategory(category1);
    
        ticket2 = new Ticket("ticket2", "descr2", 
                desiredResolutionDate2, State.APPROVED.getIndex(), Urgency.AVERAGE.getIndex());
        ticket2.setId(2L);
        ticket2.setOwner(user1);
        ticket2.setCreatedOn(creationDate2);
        ticket2.setApprover(user2);
        	
        ticket3 = new Ticket("ticket3", "descr3", 
                desiredResolutionDate3, State.IN_PROGRESS.getIndex(), Urgency.AVERAGE.getIndex());
        ticket3.setId(3L);
        ticket3.setOwner(user1);
        ticket3.setCreatedOn(creationDate3);
        ticket3.setApprover(user2);
        ticket3.setAssignee(user3);
        ticket3.setCategory(category1);
    
        ticket4 = new Ticket("ticket4", "descr4", 
                desiredResolutionDate1, State.NEW.getIndex(), Urgency.HIGH.getIndex());
        ticket4.setId(4L);
        ticket4.setOwner(user2);
        ticket4.setCreatedOn(creationDate1);
        ticket4.setCategory(category1);
    
        ticketDto1 = new TicketDto(1L, "ticket1",
                desiredResolutionDateLocal1, Urgency.HIGH, State.DRAFT);
        ticketDto2 = new TicketDto(2L, "ticket2",
                desiredResolutionDateLocal2, Urgency.AVERAGE, State.APPROVED);
        ticketDto3 = new TicketDto(3L, "ticket3",
                desiredResolutionDateLocal3, Urgency.AVERAGE, State.IN_PROGRESS);
        ticketDto4 = new TicketDto(4L, "ticket4",
                desiredResolutionDateLocal3, Urgency.HIGH, State.NEW);
    	
        ticketDescrDto = TicketDescriptionDto .newBuilder()
                .setId(3L)
                .setName("ticket3")
                .setCreationDate(creationDateLocal)
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
        ticketCreationDto.setName("ticket1");
        ticketCreationDto.setDescription("descr1");
        ticketCreationDto.setUrgencyId(Urgency.HIGH.getIndex());
        	
        actionDto1 = new ActionDto(
                Action.ASSIGN_TO_ME.getIndex(), Action.ASSIGN_TO_ME.toString());
        actionDto2 = new ActionDto(
                Action.CANCEL.getIndex(), Action.CANCEL.toString());
        objectMapper = new ObjectMapper();
    }
    
    @After
    public void resetMocks() {
        Mockito.reset(ticketServiceMock);
    }
    
    @Test
    public void testGetTickets() throws Exception {
        //Given
        Long id = 1L;
        Mockito.when(ticketServiceMock.getTicketsByUserId(id)).thenReturn(
                    Arrays.asList(ticketDto1, ticketDto2, ticketDto3, ticketDto4));  

        //When
        mockMvc.perform(get("/api/users/{id}/tickets/all", id))

        //Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE))
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("ticket1"))
        .andExpect(jsonPath("$[0].desiredResolutionDate").value("2020-03-01"))
        .andExpect(jsonPath("$[0].urgency").value("HIGH"))
        .andExpect(jsonPath("$[0].state").value("DRAFT"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("ticket2"))
        .andExpect(jsonPath("$[1].desiredResolutionDate").value("2020-03-02"))
        .andExpect(jsonPath("$[1].urgency").value("AVERAGE"))
        .andExpect(jsonPath("$[1].state").value("APPROVED"))
        .andExpect(jsonPath("$[2].id").value(3))
        .andExpect(jsonPath("$[2].name").value("ticket3"))
        .andExpect(jsonPath("$[2].desiredResolutionDate").value("2020-03-03"))
        .andExpect(jsonPath("$[2].urgency").value("AVERAGE"))
        .andExpect(jsonPath("$[2].state").value("IN_PROGRESS"))
        .andExpect(jsonPath("$[3].id").value(4))
        .andExpect(jsonPath("$[3].name").value("ticket4"))
        .andExpect(jsonPath("$[3].desiredResolutionDate").value("2020-03-03"))
        .andExpect(jsonPath("$[3].urgency").value("HIGH"))
        .andExpect(jsonPath("$[3].state").value("NEW"));

        Mockito.verify(ticketServiceMock, times(1)).getTicketsByUserId(id);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test 
    public void testGetTicketsNotFound() throws Exception {
        //Given
        Long id = 5L;
        Mockito.when(ticketServiceMock.getTicketsByUserId(id))
                .thenThrow(new  NotFoundException());
       	
        //When
        mockMvc.perform(get("/api/users/{id}/tickets/all", id))
        
        //Then
        .andExpect(status().isNotFound());
        
        Mockito.verify(ticketServiceMock, times(1)).getTicketsByUserId(id);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }

    @Test 
    public void testGetTicketsBadRequest() throws Exception {
        //Given
        String param = "param";
               	
        //When
        mockMvc.perform(get("/api/users/{id}/tickets/all", param))
        
        //Then
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testGetMyTickets() throws Exception {
        //Given
        Long id = 1L;
        Mockito.when(ticketServiceMock.getMyTicketsByUserId(id)).thenReturn(
                    Arrays.asList(ticketDto1, ticketDto2, ticketDto3));  

        //When
        mockMvc.perform(get("/api/users/{id}/tickets/my", id))

        //Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("ticket1"))
        .andExpect(jsonPath("$[0].desiredResolutionDate").value("2020-03-01"))
        .andExpect(jsonPath("$[0].urgency").value("HIGH"))
        .andExpect(jsonPath("$[0].state").value("DRAFT"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("ticket2"))
        .andExpect(jsonPath("$[1].desiredResolutionDate").value("2020-03-02"))
        .andExpect(jsonPath("$[1].urgency").value("AVERAGE"))
        .andExpect(jsonPath("$[1].state").value("APPROVED"))
        .andExpect(jsonPath("$[2].id").value(3))
        .andExpect(jsonPath("$[2].name").value("ticket3"))
        .andExpect(jsonPath("$[2].desiredResolutionDate").value("2020-03-03"))
        .andExpect(jsonPath("$[2].urgency").value("AVERAGE"))
        .andExpect(jsonPath("$[2].state").value("IN_PROGRESS"));

        Mockito.verify(ticketServiceMock, times(1)).getMyTicketsByUserId(id);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test 
    public void testGetMyTicketsNotFound() throws Exception {
        //Given
        Long id = 5L;
        Mockito.when(ticketServiceMock.getMyTicketsByUserId(id))
                .thenThrow(new  NotFoundException());
       	
        //When
        mockMvc.perform(get("/api/users/{id}/tickets/my", id))
        
        //Then
        .andExpect(status().isNotFound());
        
        Mockito.verify(ticketServiceMock, times(1)).getMyTicketsByUserId(id);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }

    @Test 
    public void testGetMyTicketsBadRequest() throws Exception {
        //Given
        String param = "param";
               	
        //When
        mockMvc.perform(get("/api/users/{id}/tickets/my", param))
        
        //Then
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testSaveTicket() throws Exception {
        //given
        Long id = 1L;
        MockMultipartFile ticket = new MockMultipartFile("ticket", "", "application/json",
                objectMapper.writeValueAsString(ticketCreationDto).getBytes());
        MockMultipartFile file = new MockMultipartFile(
                "file", "file.doc", "text/plain", "file".getBytes());
        
        //When
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/users/{id}/tickets", id)
                .file(ticket)
                .file(file))
        
        //then
        .andExpect(status().isNoContent());
        Mockito.verify(ticketServiceMock, times(1))
            .saveTicket(id, ticketCreationDto, file);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test 
    public void testSaveTicketNotFound() throws Exception {
        //Given
        Long id = 5L;
        MockMultipartFile ticket = new MockMultipartFile("ticket", "", "application/json",
                objectMapper.writeValueAsString(ticketCreationDto).getBytes());
        MockMultipartFile file = new MockMultipartFile(
                "file", "file.doc", "text/plain", "file".getBytes());
        Mockito.doThrow(new  NotFoundException())
            .when(ticketServiceMock)
            .saveTicket(id, ticketCreationDto, file);
        
        
        //When
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/users/{id}/tickets", id)
                .file(ticket)
                .file(file))
        
        //Then
        .andExpect(status().isNotFound());
        
        Mockito.verify(ticketServiceMock, times(1))
            .saveTicket(id, ticketCreationDto, file);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }

    @Test 
    public void testSaveTicketBadRequest() throws Exception {
        //Given
        String param = "param";
        MockMultipartFile ticket = new MockMultipartFile("ticket", "", "application/json",
                objectMapper.writeValueAsString(ticketCreationDto).getBytes());
        MockMultipartFile file = new MockMultipartFile(
                "file", "file.doc", "text/plain", "file".getBytes());
               	
        //When
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/users/{id}/tickets", param)
                .file(ticket)
                .file(file))
        
        //Then
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testGetTicketDtoById() throws Exception {
        //Given
        Long userId = 1L;
        Long ticketId = 3L;
        Mockito.when(ticketServiceMock.getTicketDtoById(ticketId))
                .thenReturn(ticketDescrDto);
        
        //When
        mockMvc.perform(
                get("/api/users/{id}/tickets/{ticketId}", userId, ticketId))
                
        //Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE))
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.creationDate").value("03/02/2020"))
        .andExpect(jsonPath("$.stateId").value(State.IN_PROGRESS.getIndex()))
        .andExpect(jsonPath("$.state").value(State.IN_PROGRESS.toString()))
        .andExpect(jsonPath("$.categoryId").value(1))
        .andExpect(jsonPath("$.category").value("Category1"))
        .andExpect(jsonPath("$.urgencyId").value(Urgency.AVERAGE.getIndex()))
        .andExpect(jsonPath("$.urgency").value(Urgency.AVERAGE.toString()))
        .andExpect(jsonPath("$.description").value("descr3"))
        .andExpect(jsonPath("$.desiredResolutionDate").value("03/03/2020"))
        .andExpect(jsonPath("$.ownerId").value(1))
        .andExpect(jsonPath("$.owner").value("firstName1 lastName1"))
        .andExpect(jsonPath("$.approver").value("firstName2 lastName2"))
        .andExpect(jsonPath("$.assignee").value("firstName3 lastName3"));
           
        Mockito.verify(ticketServiceMock, times(1)).getTicketDtoById(ticketId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test
    public void testGetTicketDtoByIdNotFound() throws Exception {
        //Given
        Long userId = 1L;
        Long ticketId = 5L;
        Mockito.when(ticketServiceMock.getTicketDtoById(ticketId))
                .thenThrow(new  NotFoundException());
        
        //When
        mockMvc.perform(
                get("/api/users/{id}/tickets/{ticketId}", userId, ticketId))
                
        //Then
        .andExpect(status().isNotFound());
                
        Mockito.verify(ticketServiceMock, times(1)).getTicketDtoById(ticketId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test 
    public void testGetTicketDtoByIdBadRequest() throws Exception {
        //Given
        Long userId = 1L;
        String ticketId = "ticket";
                 		
        //When
        mockMvc.perform(
                get("/api/users/{id}/tickets/{ticketId}", userId, ticketId))
        
        //Then
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testGetTicketActions() throws Exception {
        //Given
        Long userId = 3L;
        Long ticketId = 3L;
        Mockito.when(ticketServiceMock.getTicketActions(userId, ticketId)).thenReturn(
                    Arrays.asList(actionDto1, actionDto2));  

        //When
        mockMvc.perform(
                get("/api/users/{id}/tickets/{ticketId}/actions", userId, ticketId))

        //Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(Action.ASSIGN_TO_ME.getIndex()))
        .andExpect(jsonPath("$[0].name").value(Action.ASSIGN_TO_ME.toString()))
        .andExpect(jsonPath("$[1].id").value(Action.CANCEL.getIndex()))
        .andExpect(jsonPath("$[1].name").value(Action.CANCEL.toString()));

        Mockito.verify(ticketServiceMock, times(1))
            .getTicketActions(userId, ticketId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test
    public void testGetTicketActionsNotFound() throws Exception {
        //Given
        Long userId = 3L;
        Long ticketId = 3L;
        Mockito.when(ticketServiceMock.getTicketActions(userId, ticketId))
            .thenThrow(new  NotFoundException());  

        //When
        mockMvc.perform(
                get("/api/users/{id}/tickets/{ticketId}/actions", userId, ticketId))
        
        .andExpect(status().isNotFound());
        //Then
        Mockito.verify(ticketServiceMock, times(1))
            .getTicketActions(userId, ticketId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test 
    public void testGetTicketActionsBadRequest() throws Exception {
        //Given
        Long userId = 1L;
        String ticketId = "ticket";
                 		
        //When
        mockMvc.perform(
                get("/api/users/{id}/tickets/{ticketId}/actions", userId, ticketId))
        
        //Then
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testPerformAction() throws Exception {
        //Given
        Long userId = 1L;
        Long ticketId = 2L;
        int actionId = 1;
   
        //When
        mockMvc.perform(
                put("/api/users/{id}/tickets/{ticketId}/actions/{actionId}",
                        userId, ticketId, actionId))

        //Then
        .andExpect(status().isNoContent());
        Mockito.verify(ticketServiceMock, times(1))
            .performAction(userId, ticketId, actionId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test
    public void testPerformActionForbidden() throws Exception {
        //Given
        Long userId = 1L;
        Long ticketId = 2L;
        int actionId = 3;
        Mockito.doThrow(new  ActionForbiddenException())
            .when(ticketServiceMock)
                .performAction(userId, ticketId, actionId);
        //When
        mockMvc.perform(
                put("/api/users/{id}/tickets/{ticketId}/actions/{actionId}",
                        userId, ticketId, actionId))
        
        //Then
        .andExpect(status().isBadRequest());
             
        Mockito.verify(ticketServiceMock, times(1))
            .performAction(userId, ticketId, actionId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test
    public void testPerformActionNotFound() throws Exception {
        //Given
        Long userId = 1L;
        Long ticketId = 2L;
        int actionId = 3;
        Mockito.doThrow(new  NotFoundException())
            .when(ticketServiceMock)
                .performAction(userId, ticketId, actionId);
        
        //When
        mockMvc.perform(
                put("/api/users/{id}/tickets/{ticketId}/actions/{actionId}",
                        userId, ticketId, actionId))
        
        //Then
        .andExpect(status().isNotFound());
             
        Mockito.verify(ticketServiceMock, times(1))
            .performAction(userId, ticketId, actionId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test 
    public void testPerformActionBadRequest() throws Exception {
        //Given
        Long userId = 1L;
        String ticketId = "ticket";
        int actionId = 3;
                 	
        //When
        mockMvc.perform(
                put("/api/users/{id}/tickets/{ticketId}/actions/{actionId}",
                        userId, ticketId, actionId))
        
        //Then
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testEditTicket() throws Exception {
        //given
        Long userId = 1L;
        Long ticketId = 1L;
        MockMultipartFile ticket = new MockMultipartFile("ticket", "", "application/json",
                objectMapper.writeValueAsString(ticketCreationDto).getBytes());
        MockMultipartFile file = new MockMultipartFile(
                "file", "file.doc", "text/plain", "file".getBytes());
        
        //When
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/users/{id}/tickets/{ticketId}", userId, ticketId)
                .file(ticket)
                .file(file))
        //then
        .andExpect(status().isNoContent());
        Mockito.verify(ticketServiceMock, times(1))
            .editTicket(ticketId, userId, ticketCreationDto, file);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test 
    public void testEditTicketNotFound() throws Exception {
        //Given
        Long userId = 5L;
        Long ticketId = 5L;
        MockMultipartFile ticket = new MockMultipartFile("ticket", "", "application/json",
                objectMapper.writeValueAsString(ticketCreationDto).getBytes());
        MockMultipartFile file = new MockMultipartFile(
                "file", "file.doc", "text/plain", "file".getBytes());
        Mockito.doThrow(new  NotFoundException())
            .when(ticketServiceMock)
            .editTicket(ticketId, userId, ticketCreationDto, file);
        
        //When
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/users/{id}/tickets/{ticketId}", userId, ticketId)
                .file(ticket)
                .file(file))
        
        //Then
        .andExpect(status().isNotFound());
        
        Mockito.verify(ticketServiceMock, times(1))
            .editTicket(ticketId, userId, ticketCreationDto, file);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }

    @Test 
    public void testEditTicketBadRequest() throws Exception {
        //Given
        Long userId = 1L;
        String ticketId = "ticket";
        MockMultipartFile ticket = new MockMultipartFile("ticket", "", "application/json",
                objectMapper.writeValueAsString(ticketCreationDto).getBytes());
        MockMultipartFile file = new MockMultipartFile(
                "file", "file.doc", "text/plain", "file".getBytes());
               
        //When
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/users/{id}/tickets/{ticketId}", userId, ticketId)
                .file(ticket)
                .file(file))
        
        //Then
        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testDeleteAttachment() throws Exception {
        //Given
        Long userId = 1L;
        Long ticketId = 2L;
   
        //When
        mockMvc.perform(
                delete("/api/users/{id}/tickets/{ticketId}/attachments", userId, ticketId))

        //Then
        .andExpect(status().isNoContent());
        Mockito.verify(ticketServiceMock, times(1))
            .deleteAttachment(ticketId, userId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test
    public void testDeleteAttachmentNotFound() throws Exception {
        //Given
        Long userId = 5L;
        Long ticketId = 5L;
        Mockito.doThrow(new  NotFoundException())
            .when(ticketServiceMock)
            .deleteAttachment(ticketId, userId);
    
        //When
        mockMvc.perform(
                delete("/api/users/{id}/tickets/{ticketId}/attachments", userId, ticketId))

        //Then
        .andExpect(status().isNotFound());
        Mockito.verify(ticketServiceMock, times(1))
            .deleteAttachment(ticketId, userId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test
    public void testDeleteAttachmentForbidden() throws Exception {
        //Given
        Long userId = 2L;
        Long ticketId = 2L;
        Mockito.doThrow(new  ActionForbiddenException())
            .when(ticketServiceMock)
            .deleteAttachment(ticketId, userId);
    	
        //When
        mockMvc.perform(
                delete("/api/users/{id}/tickets/{ticketId}/attachments", userId, ticketId))

        //Then
        .andExpect(status().isBadRequest());
        Mockito.verify(ticketServiceMock, times(1))
            .deleteAttachment(ticketId, userId);
        Mockito.verifyNoMoreInteractions(ticketServiceMock);
    }
    
    @Test 
    public void testDeleteAttachmentBadRequest() throws Exception {
        //Given
        Long userId = 1L;
        String ticketId = "ticket";
                       	
        //When
        mockMvc.perform(
                delete("/api/users/{id}/tickets/{ticketId}/attachments", userId, ticketId))
        
        //Then
        .andExpect(status().isBadRequest());
    }
}