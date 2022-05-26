package com.example.ex24;

public interface UserService {
    public User findByUsername(String username);
    public void create(User u);
    public void delete(User u);
}
