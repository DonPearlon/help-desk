package com.training.helpdesk.history.controller;

import com.training.helpdesk.history.dto.HistoryDto;
import com.training.helpdesk.history.service.HistoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/{id}/tickets/{ticketId}/history")
@PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER','ENGINEER')")
@Api(tags = "6. Operations with ticket history events")
public class HistoryController {
    private final HistoryService historyService;
    
    /**
     * The constructor of the class.
     *
     * @param historyService - {@link HistoryService}.
     */
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * Returns the list of history events related to the ticket with specified id.
     *
     * @param id
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get history events", notes = "${HistoryController.getHistory.notes}")
    public ResponseEntity<List<HistoryDto>> getHistory(
                @PathVariable(value = "ticketId") final Long id) {
        return ResponseEntity.ok(historyService.getHistoryByTicketId(id));
    }
    
    /**
     * Returns the full list of history events related to the ticket with specified
     * id.
     *
     * @param id
     *            - id of the ticket.
     * @return {@link ResponseEntity}
     */
    @GetMapping(value = "/full",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all history events",
            notes = "${HistoryController.getFullHistory.notes}")
    public ResponseEntity<List<HistoryDto>> getFullHistory(
                @PathVariable(value = "ticketId") final Long id) {
        return ResponseEntity.ok(historyService.getFullHistoryByTicketId(id));
    }   
}