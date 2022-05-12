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


}
