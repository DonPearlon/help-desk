package com.training.helpdesk.ticket.action.service.impl;

import com.training.helpdesk.category.domain.Category;
import com.training.helpdesk.commons.exceptions.ActionForbiddenException;
import com.training.helpdesk.ticket.action.domain.Action;
import com.training.helpdesk.ticket.domain.Ticket;
import com.training.helpdesk.ticket.domain.state.State;
import com.training.helpdesk.ticket.domain.urgency.Urgency;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.domain.role.Role;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the {@link TicketActionServiceImpl} class.
 *
 * @author Alexandr_Terehov
 */
public class TicketActionServiceImplTest {
    private TicketActionServiceImpl ticketActionServiceImpl;
    
    private User user1;
    private User user2;
    private User user3;

    private Ticket ticket1;
    private Ticket ticket2;
    private Ticket ticket3;
    
    private Date creationDate1;
    private Date creationDate2;
    private Date creationDate3;
    
    private Date desiredResolutionDate1;
    private Date desiredResolutionDate2;
    private Date desiredResolutionDate3;
    
    private Category category;
    
    @Before
    public void initObjects() {
        ticketActionServiceImpl = new TicketActionServiceImpl();    	
    
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
    
        category = new Category();
        category.setId(1L);
        category.setName("Category1");
    
        ticket1 = new Ticket("Ticket1", "descr1", 
                desiredResolutionDate1, State.DRAFT.getIndex(), Urgency.HIGH.getIndex());
        ticket1.setId(1L);
        ticket1.setOwner(user1);
        ticket1.setCreatedOn(creationDate1);
        ticket1.setCategory(category);
    
        ticket2 = new Ticket("Ticket2", "descr2", 
                desiredResolutionDate2, State.NEW.getIndex(), Urgency.AVERAGE.getIndex());
        ticket2.setId(2L);
        ticket2.setOwner(user1);
        ticket2.setCreatedOn(creationDate2);
        ticket2.setCategory(category);
        	
        ticket3 = new Ticket("Ticket3", "descr3", 
                desiredResolutionDate3, State.APPROVED.getIndex(), Urgency.AVERAGE.getIndex());
        ticket3.setId(3L);
        ticket3.setOwner(user1);
        ticket3.setCreatedOn(creationDate3);
        ticket3.setApprover(user2);
        ticket3.setCategory(category);
    }
    
    @Test
    public void testGetActionsEmployee() {
        //given
        List<Action> expectedActions = Arrays.asList(Action.SUBMIT, Action.CANCEL);
    
        //when
        List<Action> resultActions = ticketActionServiceImpl.getActions(user1, ticket1);
    
        //then
        assertEquals(expectedActions, resultActions);
    }
    
    @Test
    public void testGetActionsManager() {
        //given
        List<Action> expectedActions 
            = Arrays.asList(Action.APPROVE, Action.DECLINE, Action.CANCEL);
    
        //when
        List<Action> resultActions = ticketActionServiceImpl.getActions(user2, ticket2);
    
        //then
        assertEquals(expectedActions, resultActions);
    }
    
    @Test
    public void testGetActionsEngineer() {
        //given
        List<Action> expectedActions 
            = Arrays.asList(Action.ASSIGN_TO_ME, Action.CANCEL);
    
        //when
        List<Action> resultActions = ticketActionServiceImpl.getActions(user3, ticket3);
    
        //then
        assertEquals(expectedActions, resultActions);
    }
    
    @Test
    public void testGetActionsEmpty() {
        //given
        Ticket ticket = new Ticket("Ticket", "descr", 
                desiredResolutionDate1, State.DRAFT.getIndex(), Urgency.HIGH.getIndex());
        ticket.setId(1L);
        ticket.setOwner(user2);
        ticket.setCreatedOn(creationDate1);
        ticket.setCategory(category);
    
        //when
        List<Action> resultActions = ticketActionServiceImpl.getActions(user1, ticket);
    
        //then
        assertTrue(resultActions.isEmpty());
    }
    
    @Test
    public void testCheckActionEmployee() {
        //given
        Action action = Action.SUBMIT;
    
        //when
        boolean resultCheckAction
            = ticketActionServiceImpl.checkAction(user1, ticket1, action);
    
        //then
        assertTrue(resultCheckAction);
    }
    
    @Test
    public void testCheckActionManager() {
        //given
        Action action = Action.APPROVE;
    
        //when
        boolean resultCheckAction
            = ticketActionServiceImpl.checkAction(user2, ticket2, action);
    
        //then
        assertTrue(resultCheckAction);
    }
    
    @Test
    public void testCheckActionEngineer() {
        //given
        Action action = Action.ASSIGN_TO_ME;
    
        //when
        boolean resultCheckAction
            = ticketActionServiceImpl.checkAction(user3, ticket3, action);
    
        //then
        assertTrue(resultCheckAction);
    }
    
    @Test(expected = ActionForbiddenException.class)
    public void testCheckActionEmployeeNegative() {
        //given
        Action action = Action.APPROVE;
    
        //when
        ticketActionServiceImpl.checkAction(user1, ticket1, action);
    } 
    
    @Test(expected = ActionForbiddenException.class)
    public void testCheckActionManagerNegative() {
        //given
        Action action = Action.SUBMIT;
    
        //when
        ticketActionServiceImpl.checkAction(user2, ticket1, action);
    }
    
    @Test(expected = ActionForbiddenException.class)
    public void testCheckActionEngineerNegative() {
        //given
        Action action = Action.DECLINE;
    
        //when
        ticketActionServiceImpl.checkAction(user3, ticket3, action);
    } 
}