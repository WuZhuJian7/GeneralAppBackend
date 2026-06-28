package com.general.app.module.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.general.app.common.constant.ResultCode;
import com.general.app.common.exception.BusinessException;
import com.general.app.common.result.PageResult;
import com.general.app.module.user.entity.User;
import com.general.app.module.user.entity.dto.UserDTO;
import com.general.app.module.user.entity.query.UserQuery;
import com.general.app.module.user.entity.vo.UserVO;
import com.general.app.module.user.mapper.UserMapper;
import com.general.app.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户Service实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO register(UserDTO userDTO) {
        // 检查用户名是否已存在
        User existingUser = getByUsername(userDTO.getUsername());
        if (existingUser != null) {
            throw new BusinessException(ResultCode.CONFLICT, "用户名已存在");
        }

        // 创建用户
        User user = new User();
        BeanUtil.copyProperties(userDTO, user, "id");
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setStatus(1);

        save(user);
        log.info("用户注册成功: {}", user.getUsername());

        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public User getByUsername(String username) {
        return lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getDeleted, 0)
                .one();
    }

    @Override
    public PageResult<UserVO> page(UserQuery query) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(query.getUsername()), User::getUsername, query.getUsername())
                .eq(StringUtils.hasText(query.getPhone()), User::getPhone, query.getPhone())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .orderByDesc(User::getCreateTime);

        Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<User> result = page(page, wrapper);

        List<UserVO> records = result.getRecords().stream()
                .map(user -> BeanUtil.copyProperties(user, UserVO.class))
                .collect(Collectors.toList());

        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), records);
    }

    @Override
    public UserVO getDetail(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserDTO userDTO) {
        User user = getById(userDTO.getId());
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        // 如果修改了密码
        if (StringUtils.hasText(userDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        BeanUtil.copyProperties(userDTO, user, "id", "password");
        updateById(user);
        log.info("用户信息更新成功: {}", user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        removeById(id);
        log.info("用户删除成功: {}", user.getUsername());
    }
}
