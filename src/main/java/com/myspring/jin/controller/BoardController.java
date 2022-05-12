package com.myspring.jin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.myspring.jin.domain.BoardDto;
import com.myspring.jin.domain.CommentDto;
import com.myspring.jin.domain.PageHandler;
import com.myspring.jin.domain.SearchCondition;
import com.myspring.jin.service.BoardService;
import com.myspring.jin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    CommentService commentService;



    @PostMapping("/update")
    public String update(BoardDto boardDto, HttpSession session, RedirectAttributes rattr, Model m
    , Integer page, Integer pageSize){
            String writer = (String)session.getAttribute("id");
            boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.update(boardDto);

            if(rowCnt!=1)
                throw new Exception("delete failed");

            rattr.addFlashAttribute("msg","UPD_OK");

            return "redirect:/board/list?page="+page+"&pageSize="+pageSize;

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            rattr.addFlashAttribute("msg","UPD_ERR");
            return "board";
        }
    }




    @PostMapping("/delete")
    public String delete(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        try {

            int rowCnt = boardService.delete(bno, writer);

            if(rowCnt!=1)
                throw new Exception("board delete error");

            rattr.addFlashAttribute("msg", "DEL_OK");

        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }

        return "redirect:/board/list?page="+page+"&pageSize="+pageSize;
    }


    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.write(boardDto); // insert

            if(rowCnt!=1)
                throw new Exception("Write failed");

            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            m.addAttribute("msg", "WRT_ERR");
            return "board";
        }
    }

    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode","new");
        return "board";
    }



    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m){

        try {
            BoardDto boardDto = boardService.read(bno);
            m.addAttribute(boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board";
    }



    @GetMapping("/list")
    public String list(SearchCondition sc, HttpServletRequest request, Model m) {
        if(sc.getPage()==null){
            sc.setPage(1);
        }
        if(sc.getPageSize()==null){
            sc.setPageSize(10);
        }

        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        try {
            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list",list);
            m.addAttribute("ph",pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "List_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}
