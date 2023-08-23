package org.example.dao;

import org.example.model.User;

public interface UserDao {
    public void save(User u,int otp);
    public void remove(User u);
    public int get(User u);

}
