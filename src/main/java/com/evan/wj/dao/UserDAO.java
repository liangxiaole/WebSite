package com.evan.wj.dao;

import com.evan.wj.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {

    User SelByUserName(String username);
    public void insert(User user);
}
