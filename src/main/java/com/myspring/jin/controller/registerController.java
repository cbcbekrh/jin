package com.myspring.jin.controller;

import com.myspring.jin.domain.CommentDto;
import com.myspring.jin.domain.UserDto;
import com.myspring.jin.service.MailSendService;
import com.myspring.jin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


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
