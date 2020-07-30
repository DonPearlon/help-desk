package com.training.helpdesk.commons.security;

import com.training.helpdesk.commons.exceptions.NotFoundException;
import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of the {@link UserDetailsService}.
 *
 * @author Alexandr_Terehov
 */
@Service
public class HelpDeskUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    
    @Autowired
    public HelpDeskUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * 
     * @param username
     *            name of the user.
     * 
     * @return instance of the {@link HelpDeskUserPrincipal}.
     * 
     * @throws NotFoundException
     *                 if user was not found.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        User user = userRepository.getUserByEmail(username)
                    .orElseThrow(() ->  new NotFoundException("User not found"));
        return new HelpDeskUserPrincipal(user);
    }
}
