package com.hwj.classroom.vod.controller;


import com.hwj.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/vod/user")
@Api(tags ="登录注册")
public class UserLoginController {

    //login
    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result login() {
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);
    }

    //info
    @GetMapping("info")
    @ApiOperation(value = "获取用户信息")
    public Result info() {
        Map<String,Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
        return Result.ok(map);
    }
}
