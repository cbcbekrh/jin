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

    @Override
    public int idCheck(String id)throws Exception{
        return userDao.idCheck(id);
    }

    @Override
    public int insertMA(String mail, String authKey)throws Exception{
        return userDao.insertMA(mail, authKey);
    }

    @Override
    public String mailAuth(String mail) throws Exception{
        return userDao.mailAuth(mail);
    }

    @Override
    public int delete_AuthMail(String mail)throws Exception{
        return userDao.delete_AuthMail(mail);
    }

    @Override
    public int plus(String mail, String Auth_Key) throws Exception{
        return userDao.plus(mail, Auth_Key);
    }

    @Override
    public int Cnt(String mail)throws Exception{
        return userDao.Cnt(mail);
    }
}
