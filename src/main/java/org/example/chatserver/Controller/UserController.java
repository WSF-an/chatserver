package org.example.chatserver.Controller;



import lombok.extern.slf4j.Slf4j;
import org.example.chatserver.dto.User;
import org.example.chatserver.dto.UserRegister;
import org.example.chatserver.dto.vo.UserVO;
import org.example.chatserver.service.Userservice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private Userservice userservice;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestBody UserRegister user) {
        if(user==null){
            throw new RuntimeException("参数为空");
        }
        String userAccount = user.getUserAccount();
        String userPassword = user.getUserPassword();
        long result  = userservice.userRegister(userAccount,userPassword);
        log.info("注册成功"+result);
        return "注册成功"+String.valueOf(result);
    }

    /**
     * 用户登录
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/login")
    public UserVO login(@RequestBody UserRegister user, HttpServletRequest request) {
        if(user==null){
            throw new RuntimeException("输入不合法");
        }
        String userAccount = user.getUserAccount();
        String userPassword = user.getUserPassword();
        UserVO userVO= userservice.userLogin(userAccount,userPassword,request);
        return userVO;
    }
    @GetMapping("/get/login")
    public UserVO getLogin(HttpServletRequest request) {
        User user = userservice.getLoginUser(request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }
}
