package com.myspring.jin.dao;

import com.myspring.jin.domain.MailAuthVO;
import com.myspring.jin.domain.UserDto;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    int insert(UserDto dto) throws Exception;

    UserDto select(String id) throws Exception;

    String look(String id, String email)throws Exception;

    int idCheck(String id)throws Exception;

    int insertMA(String mail, String authKey) throws Exception;

    String mailAuth(String mail)throws Exception;

    int delete_AuthMail(String mail)throws Exception;

    int plus(String mail, String auth_key)throws Exception;

    int Cnt(String mail)throws Exception;
}

