package com.evan.wj.controller;

import com.evan.wj.pojo.User;
import com.evan.wj.result.Result;
import com.evan.wj.result.ResultFactory;
import com.evan.wj.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

import java.util.Objects;

@Controller
public class LoginController {
@Autowired
private UserService userService;
    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser,HttpSession session) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        System.out.println(username);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        /**
         * 并没有设置存活时间，所以在关闭浏览器后，sessionId 就会消失，再次发送请求，shiro 就会认为用户已经变更。但有时我们需要保持登录状态，不然每次都要重新登录怪麻烦的，所以 shiro 提供了 rememberMe 机制。
         *
         * rememberMe 机制不是单纯地设置 cookie 存活时间，而是又单独保存了一种新的状态。之所以这样设计，也是出于安全性考虑，把 “记住我” 的状态与实际登录状态做出区分，这样，就可以控制用户在访问不太敏感的页面时无需重新登录，而访问类似于购物车、订单之类的页面时必须重新登录。
         */
        usernamePasswordToken.setRememberMe(true);
        try{
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(username);
        }catch (AuthenticationException e) {
            String message = "账号密码错误";
            return ResultFactory.buildFailResult(message);
        }

/**
 * 普通不用shiro框架登录注释

        User user = userService.selByUsername(username);
        if(user.getUsername().equals(username)&&requestUser.getPassword().equals(user.getPassword())){
            System.out.println("已经查到数据了");
            session.setAttribute("user", user);
            return new Result(200);
        }else {
            System.out.println("没有查到数据");
            String message = "账号密码错误";
            System.out.println("test");
            return new Result(400);
        }

 */
//        if (!Objects.equals("admin", username) || !Objects.equals("123456", requestUser.getPassword())) {
//            String message = "账号密码错误";
//            System.out.println("test");
//            return new Result(400);
//        } else {
//            return new Result(200);
//        }
    }

    @CrossOrigin
    @PostMapping(value = "api/register")
    @ResponseBody
    public Result register (@RequestBody User user){
        String message = "";
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        User user1 = userService.selByUsername(username);
        if(user1!=null){
             message = "库中已存在该用户，请换其他用户名 注册！！！！";
            return ResultFactory.buildFailResult(message);
        }
        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setSalt(salt);
        userService.insert(user);
        message = "注册成功，请前往登录页登录";
        return ResultFactory.buildSuccessResult(message);
    }

    @CrossOrigin
    @GetMapping("/api/logout")
    @ResponseBody
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功退出";
        return ResultFactory.buildSuccessResult(message);

    }
    @ResponseBody
    @GetMapping(value = "api/authentication")
    public String authentication(){
        return "身份认证成功";
    }


    /**
     *
     * 现在还存在一个问题，即后端接口的拦截是实现了，但页面的拦截并没有实现，仍然可以通过伪造参数，绕过前端的路由限制，访问本来需要登录才能访问的页面。为了解决这个问题，我们可以修改 router.beforeEach 方法：
     *
     * router.beforeEach((to, from, next) => {
     *     if (to.meta.requireAuth) {
     *       if (store.state.user) {
     *         axios.get('/authentication').then(resp => {
     *           if (resp) next()
     *         })
     *       } else {
     *         next({
     *           path: 'login',
     *           query: {redirect: to.fullPath}
     *         })
     *       }
     *     } else {
     *       next()
     *     }
     *   }
     * )
     * 即访问每个页面前都向后端发送一个请求，
     * 目的是经由拦截器验证服务器端的登录状态，防止上述情况的发生。后端这个接口可以暂时写成空的
     *
     */
}
