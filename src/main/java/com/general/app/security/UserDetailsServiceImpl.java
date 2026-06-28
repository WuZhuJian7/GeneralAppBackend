package com.general.app.security;

import com.general.app.module.user.entity.User;
import com.general.app.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * UserDetailsService 实现
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new UsernameNotFoundException("账号已被禁用: " + username);
        }

        // 这里可以根据业务需求查询用户权限
        // Set<String> permissions = permissionService.getByUserId(user.getId());
        Set<String> permissions = new HashSet<>();
        permissions.add("ROLE_USER");  // 默认角色

        return new LoginUser(user.getId(), user.getUsername(), user.getPassword(), permissions);
    }
}
