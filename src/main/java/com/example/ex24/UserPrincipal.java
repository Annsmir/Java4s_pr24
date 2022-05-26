package com.example.ex24;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private User user;

    
    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> ga = new ArrayList<GrantedAuthority>();
        ga.add(new SimpleGrantedAuthority("ROLE_USER")); // in real app this should be read from DB
        return ga;
    }

    @Override
    public String getPassword() {
        
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
       
        return user.getEnabled() != 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return user.getEnabled() != 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return user.getEnabled() != 0;
    }

    @Override
    public boolean isEnabled() {
        
        return user.getEnabled() != 0;
    }


}
