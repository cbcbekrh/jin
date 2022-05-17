package com.myspring.jin.controller;


import com.myspring.jin.domain.CommentDto;
import com.myspring.jin.service.CommentService;
import com.myspring.jin.service.MailSendService;
import com.myspring.jin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class CommentController {
    @Autowired
    CommentService service;
    @Autowired
    UserService userService;
    @Inject
    MailSendService mailSendService;  //@Service를 붙였었다.

    @RequestMapping("/Cnt")
    @ResponseBody
    public int sub(String mail) {

        int Cnt = 0;
        try {
            Cnt = userService.Cnt(mail);
            userService.delete_AuthMail(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Cnt;
    }


    @RequestMapping("/mailAuth.wow")
    @ResponseBody
    public String mailAuth(String mail) {
        String authKey = null;
        try {
            userService.delete_AuthMail(mail);
            authKey = mailSendService.sendAuthMail(mail);
            userService.insertMA(mail, authKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authKey;
    }

    @RequestMapping("/mailAuthN.wow")
    @ResponseBody
    public String mailAuthN(@RequestParam Map <String, String>map, HttpServletResponse resp){
        String Auth_Key = map.get("Number");
        String mail = map.get("mail");

        String Auth_Key2 = null;
        String result = "";
        try {
            Auth_Key2 = userService.mailAuth(mail);

            if(Auth_Key.equals(Auth_Key2)){
               int Cnt = userService.plus(mail,Auth_Key);
                result = "OK";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "ERR";
        }
    }


    @ResponseBody
    @PostMapping("/idCheck")
    public int idCheck(@RequestBody String id) throws Exception{
            int Cnt = userService.idCheck(id);
            return Cnt;
    }

    @GetMapping("/comments") // /comments?bno=1080 GET
    @ResponseBody public ResponseEntity<List<CommentDto>> list(Integer bno){
        List<CommentDto> list = null;
        try {
            list = service.getList(bno);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400
        }
    }

    @DeleteMapping("/comments/{cno}") // /comments/1 <-- 삭제할 댓글 번호
    @ResponseBody
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session){
       String commenter = (String)session.getAttribute("id");

        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session){
        String commenter = (String)session.getAttribute("id");
        dto.setCommenter(commenter);
        dto.setBno(bno);
        try{
            if(service.write(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PatchMapping("/comments/{cno}")
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session){
        String commenter = (String)session.getAttribute("id");
        dto.setCommenter(commenter);
        dto.setCno(cno);

        try {
            if(service.modify(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }
}
