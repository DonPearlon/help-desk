package com.training.helpdesk.user.service.impl;

import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.user.converter.UserConverter;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.dto.UserDto;
import com.training.helpdesk.user.repository.UserRepository;
import com.training.helpdesk.user.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link UserService} interface.
 *
 * @author Alexandr_Terehov
 */
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_ID
            = "User with Id:%s was not found.";

    private final UserRepository repository;
    private final UserConverter converter;
    
    /**
     * Constructor.
     *
     * @param repository
     *            {@link UserRepository}.
     * @param converter
     *            {@link UserConverter}.
     */
    public UserServiceImpl(final UserRepository repository, final UserConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }
    
    /**
     * @return List of all 'Managers' contained in the database.
     */
    @Transactional(readOnly = true)
    public List<UserDto> getManagers() {
        return repository.getManagers().stream().map(converter::toDto).collect(Collectors.toList());
    }
    
    /**
     * @return List of all 'Engineers' contained in the database.
     */
    @Transactional(readOnly = true)
    public List<UserDto> getEngineers() {
        return repository.getEngineers().stream().map(converter::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get UserDto object by ID of the user.
     *
     * @param id
     *            id of the user.
     * @return object of the {@link UserDto} class.
     * @throws {@link NotFoundException}
     *            if user was not found
     */
    @Transactional(readOnly = true)
    public UserDto getUserDtoById(final Long id) {
        return converter.toDto(repository.getUserById(id)
            .orElseThrow(() ->  new NotFoundException(String.format(USER_NOT_FOUND_ID, id))));
    }
    
    /**
     * Get UserDto object by the Id.
     *
     * @param id
     *            id of the user.
     * @return object of the {@link User} class.
     * @throws {@link NotFoundException}
     *            if user was not found
     */
    @Transactional(readOnly = true)
    public User getUserById(final Long id) {
        return repository.getUserById(id)
            .orElseThrow(() ->  new NotFoundException(String.format(USER_NOT_FOUND_ID, id)));
    }
}