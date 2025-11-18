package com.yychainsaw.controller;

import com.yychainsaw.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    public Result<String> list(/*@RequestHeader(name = "Authorization") String token, HttpServletResponse response*/) {
        /*try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return Result.success("文章列表...");
        } catch (Exception e) {
            response.setStatus(401);
            return Result.error("token无效或已过期|未登录");
        }*/
        return Result.success("文章列表...");
    }
}
