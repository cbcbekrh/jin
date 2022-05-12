package com.myspring.jin.service;

import com.myspring.jin.dao.UserDao;
import com.myspring.jin.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public int write(UserDto userDto) throws Exception {
        return userDao.insert(userDto);
    }

    @Override
    public UserDto read(String id) throws Exception {
        return userDao.select(id);
    }

    @Override
    public String look(String id, String email)throws Exception{
        return userDao.look(id, email);
    }


}
