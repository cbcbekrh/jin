package com.myspring.jin.service;


import com.myspring.jin.domain.UserDto;


public interface UserService {
    int write(UserDto userDto) throws Exception;

    UserDto read(String id) throws Exception;

    String look(String id, String email)throws Exception;




}
