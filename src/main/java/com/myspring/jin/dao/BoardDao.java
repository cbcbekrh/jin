package com.myspring.jin.dao;

import com.myspring.jin.domain.BoardDto;
import com.myspring.jin.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardDao {
    List<BoardDto> selectPage(Map map) throws Exception;

    int count() throws Exception;

    BoardDto read(Integer bno) throws Exception;

    int view_cnt(Integer bno) throws Exception;

    int write(BoardDto dto) throws Exception;

    int update(BoardDto dto)throws Exception;

    int delete(Integer bno, String writer) throws Exception;


    List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception;
    int searchResultCnt(SearchCondition sc)throws Exception;

    int updateCommentCnt(Integer bno, int cnt);
}
