package com.tomkat.flashboot.dao;

import com.tomkat.flashboot.entity.User;

public interface UserDAO {
    public User findUser(String email);
}
