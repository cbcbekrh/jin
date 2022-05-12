package com.myspring.jin.controller;

import com.myspring.jin.domain.UserDto;
import com.myspring.jin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.LifecycleProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 1. 세션을 종료
        session.invalidate();
        // 2. 홈으로 이동
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String id, String pwd, boolean rememberId, String toURL,
                        HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 1. id와 pwd를 확인
        if(!loginCheck(id, pwd)) {
            // 2-1   일치하지 않으면, loginForm으로 이동
            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "UTF-8");
            return "redirect:/login/login?msg="+msg;
        }

        HttpSession session = request.getSession();
        session.setAttribute("id", id);

        if(rememberId){
            Cookie cookie = new Cookie("id", id);
            response.addCookie(cookie);
        }else{
            Cookie cookie = new Cookie("id", id);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        toURL = toURL==null || toURL.equals("") ? "/" : toURL;
        return "redirect:"+toURL;
    }

    private boolean loginCheck(String id, String pwd) {
        UserDto userDto = null;

        try {
            userDto = userService.read(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return userDto!=null && userDto.getPwd().equals(pwd);
    }
}