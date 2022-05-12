package com.myspring.jin.controller;

import com.myspring.jin.domain.UserDto;
import com.myspring.jin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URLEncoder;

@Controller
@RequestMapping("/register")
public class registerController {
    @Autowired
    UserService userService;

    @InitBinder
    public void Validator(WebDataBinder binder){
        binder.setValidator(new UserValidator());
    }


    @GetMapping("/add")
    public String register(){
        return "registerForm";
    }

    @PostMapping("/add")
    public String save(@Valid UserDto userDto, BindingResult result, Model m) throws Exception{
        // 유효성검사 하고 실패하면 돌려보내고 성공하면 저장하고 메인홈으로 가게 만들자.

        if(!result.hasErrors()){
            int rowCnt = 0;

            try {
                rowCnt = userService.write(userDto);
            } catch (Exception e) {
                e.printStackTrace();
                m.addAttribute("msg1","Id_ERR");
            }

            if(rowCnt!=0){
                return "index";
            }
        }
        return "registerForm";
    }
}
