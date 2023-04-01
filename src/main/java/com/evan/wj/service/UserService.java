package com.evan.wj.service;

import com.evan.wj.dao.UserDAO;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
    UserDAO userDAO;

    public User selByUsername(String username){
    return userDAO.SelByUserName(username);
    }

    public void insert(User user){
        userDAO.insert(user);
    }
}
