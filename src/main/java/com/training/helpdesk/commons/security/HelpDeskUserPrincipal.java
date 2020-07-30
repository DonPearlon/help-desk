package com.training.helpdesk.commons.security;

import com.training.helpdesk.user.domain.User;
import com.training.helpdesk.user.domain.role.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of the {@link UserDetails} interface. Represents user
 * of the 'Help-Desk' app.
 *
 * @author Alexandr_Terehov
 */
public class HelpDeskUserPrincipal implements UserDetails {
    
    private static final long serialVersionUID = 6568657457345114247L;

    private final User user;

    public HelpDeskUserPrincipal(final User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(
                Role.getByIndex(user.getRoleId()).toString()));
        return authorities;
    }

    /**
     * @return id of the user.
     */
    public Long getUserId() {
        return user.getId();
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}