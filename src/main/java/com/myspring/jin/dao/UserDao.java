package com.myspring.jin.dao;

import com.myspring.jin.domain.UserDto;

public interface UserDao {
    int insert(UserDto dto) throws Exception;

    UserDto select(String id) throws Exception;

    String look(String id, String email)throws Exception;


}

