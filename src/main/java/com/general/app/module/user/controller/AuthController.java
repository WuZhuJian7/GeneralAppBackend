package com.general.app.module.user.controller;

import com.general.app.common.result.Result;
import com.general.app.security.JwtUtil;
import com.general.app.security.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证Controller
 */
@Tag(name = "认证管理", description = "登录/登出接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest request) {
        // 认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 获取用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 生成Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", loginUser.getUserId());
        String token = jwtUtil.generateToken(loginUser.getUsername(), claims);

        // 存入Redis（可用于踢人下线）
        redisTemplate.opsForValue().set(
                "token:" + loginUser.getUsername(),
                token,
                24,
                TimeUnit.HOURS
        );

        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("username", loginUser.getUsername());
        result.put("userId", loginUser.getUserId());

        return Result.success(result);
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        // 从SecurityContextHolder获取当前用户
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            // 删除Redis中的Token
            redisTemplate.delete("token:" + loginUser.getUsername());
        }
        return Result.success();
    }
}
