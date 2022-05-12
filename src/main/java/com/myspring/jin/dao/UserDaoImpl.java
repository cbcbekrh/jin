package com.myspring.jin.dao;

import com.myspring.jin.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.myspring.jin.dao.UserMapper.";

    @Override
    public int insert(UserDto Dto) throws Exception{
        return session.insert(namespace + "insert", Dto);
    }

    @Override
    public UserDto select(String id) throws Exception {
        return session.selectOne(namespace + "select", id);
    }

    @Override
    public String look(String id, String email)throws Exception{
        Map map = new HashMap();
        map.put("id", id);
        map.put("email", email);
        return session.selectOne(namespace+"look", map);
    }

    @Override
    public int idCheck(String id)throws Exception{
        return session.selectOne(namespace+"idCheck",id);
    }

    @Override
    public int insertMA(String mail, String authKey)throws Exception{
        Map map = new HashMap();
        map.put("mail", mail);
        map.put("authKey", authKey);
        return session.selectOne(namespace+"insertMA", map);
    }

    @Override
    public String mailAuth(String mail)throws Exception{
        return session.selectOne(namespace+"mailAuth",mail);
    }

    @Override
    public int delete_AuthMail(String mail)throws Exception{
        return session.delete(namespace+"delete_AuthMail",mail);
    }

    @Override
    public int plus(String mail, String Auth_Key)throws Exception{
        Map map = new HashMap();
        map.put("mail", mail);
        map.put("Auth_Key", Auth_Key);
        return session.update(namespace+"plus",map);
    }

    @Override
    public int Cnt(String mail)throws Exception{
        return session.selectOne(namespace+"Cnt",mail);
    }
}
