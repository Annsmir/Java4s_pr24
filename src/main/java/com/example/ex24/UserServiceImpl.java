package com.example.ex24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository users;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void create(User u) {
        users.saveAndFlush(u);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        User user = users.findByUsername(username);
        return user;
    }
    
    @Override
    @Transactional(readOnly = false)
    public void delete(User u) {
        users.delete(u);
    }
}
