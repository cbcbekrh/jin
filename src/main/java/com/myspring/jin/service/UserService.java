package com.myspring.jin.service;


import com.myspring.jin.domain.UserDto;


public interface UserService {
    int write(UserDto userDto) throws Exception;

    UserDto read(String id) throws Exception;

    String look(String id, String email)throws Exception;

    int idCheck(String id)throws Exception;

    int insertMA(String mail, String authKey) throws Exception;

    String mailAuth(String mail)throws Exception;

    int delete_AuthMail(String mail) throws Exception;

    int plus(String mail, String Auth_Key) throws Exception;

    int Cnt(String mail)throws Exception;
}
