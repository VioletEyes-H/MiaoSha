package com.bula.ms.controller;

import com.bula.ms.entity.User;
import com.bula.ms.result.Result;
import com.bula.ms.service.UserService;
import com.bula.ms.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping(method = RequestMethod.POST)
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Result<String> login(HttpServletResponse response, @Valid LoginVo loginVo) {
        String token = userService.login(response, loginVo);
        return Result.success(token);
    }
}
