package com.training.helpdesk.context;

import com.training.helpdesk.ticket.service.TicketService;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContext {

    @Bean
    @Qualifier("ticketServiceMock") 
    public TicketService ticketService() {
        return Mockito.mock(TicketService.class);
    }
}