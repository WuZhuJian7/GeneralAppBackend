package com.general.app.module.user.controller;

import com.general.app.common.result.PageResult;
import com.general.app.common.result.Result;
import com.general.app.module.user.entity.dto.UserDTO;
import com.general.app.module.user.entity.query.UserQuery;
import com.general.app.module.user.entity.vo.UserVO;
import com.general.app.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户Controller
 */
@Tag(name = "用户管理", description = "用户CRUD接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<UserVO> register(@Valid @RequestBody UserDTO userDTO) {
        UserVO userVO = userService.register(userDTO);
        return Result.success(userVO);
    }

    @Operation(summary = "分页查询用户列表")
    @GetMapping("/page")
    public Result<PageResult<UserVO>> page(UserQuery query) {
        PageResult<UserVO> result = userService.page(query);
        return Result.success(result);
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public Result<UserVO> detail(@PathVariable Long id) {
        UserVO userVO = userService.getDetail(id);
        return Result.success(userVO);
    }

    @Operation(summary = "更新用户信息")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }
}
