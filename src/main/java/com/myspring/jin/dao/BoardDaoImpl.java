package com.myspring.jin.dao;

import com.myspring.jin.domain.BoardDto;
import com.myspring.jin.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDaoImpl implements BoardDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.myspring.jin.dao.BoardMapper.";

    @Override
    public List<BoardDto> selectPage(Map map) throws Exception{
        return session.selectList(namespace + "selectPage", map);
    }

    @Override
    public int count()throws Exception{
        return session.selectOne(namespace+"count");
    }

    @Override
    public BoardDto read(Integer bno)throws Exception{
        return session.selectOne(namespace+ "read" ,bno);
    }

    @Override
    public int view_cnt(Integer bno) throws Exception{
        return session.update(namespace+"view_cnt",bno);
    }

    @Override
    public int write(BoardDto dto)throws Exception{
        return session.insert(namespace+"write",dto);
    }

    @Override
    public int update(BoardDto dto)throws Exception{
        return session.insert(namespace+"update",dto);
    }

    @Override
    public int delete(Integer bno, String writer)throws Exception{
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace+"delete",map);
    }

    @Override
    public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception{
        return session.selectList(namespace + "searchSelectPage", sc);
    }

    @Override
    public int searchResultCnt(SearchCondition sc)throws Exception{
        return session.selectOne(namespace+"searchResultCnt",sc);
    }

    @Override
    public int updateCommentCnt(Integer bno, int cnt) {
        Map map = new HashMap();
        map.put("cnt", cnt);
        map.put("bno", bno);
        return session.update(namespace+"updateCommentCnt", map);
    }
}
