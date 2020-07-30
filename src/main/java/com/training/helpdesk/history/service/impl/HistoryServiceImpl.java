package com.training.helpdesk.history.service.impl;

import com.training.helpdesk.history.converter.HistoryConverter;
import com.training.helpdesk.history.domain.History;
import com.training.helpdesk.history.dto.HistoryDto;
import com.training.helpdesk.history.repository.HistoryRepository;
import com.training.helpdesk.history.service.HistoryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link HistoryService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class HistoryServiceImpl implements HistoryService {
    private static final int ROW_LIMIT = 5;

    private final HistoryRepository repository;
    private final HistoryConverter converter;
    
    /**
     * Constructor
     * 
     * @param repository
     *            object implements {@link HistoryRepository} interface to set.
     * @param converter
     *            instance of the {@link HistoryConverter} class to set. 
     */
    public HistoryServiceImpl(
            final HistoryRepository repository, final HistoryConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns list of history events contained in the database
     *         related to the ticket with specified id.
     */
    @Transactional 
    public List<HistoryDto> getHistoryByTicketId(final Long id) {
        return repository.getHistoryByTicketId(id, ROW_LIMIT)
            .stream().map(converter::toDto).collect(Collectors.toList());
    }
    
    /**
     * 
     * @param id
     *            id of the ticket.
     * @return Returns full list of history events contained in the database
     *         related to the ticket with specified id.
     */
    @Transactional 
    public List<HistoryDto> getFullHistoryByTicketId(final Long id) {
        return repository.getHistoryByTicketId(id)
            .stream().map(converter::toDto).collect(Collectors.toList());
    }
    
    /**
     * Saves information about the history event.
     *
     * @param history
     *            instance of the {@link History} class.
     */
    @Transactional
    public void saveHistoryEvent(final History history) {
        repository.insertHistoryEvent(history);
    }
}