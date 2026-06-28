package com.general.app.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.general.app.common.result.PageResult;
import com.general.app.module.user.entity.User;
import com.general.app.module.user.entity.dto.UserDTO;
import com.general.app.module.user.entity.query.UserQuery;
import com.general.app.module.user.entity.vo.UserVO;

/**
 * 用户Service接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     */
    UserVO register(UserDTO userDTO);

    /**
     * 根据用户名查询用户
     */
    User getByUsername(String username);

    /**
     * 分页查询用户
     */
    PageResult<UserVO> page(UserQuery query);

    /**
     * 获取用户详情
     */
    UserVO getDetail(Long id);

    /**
     * 更新用户信息
     */
    void update(UserDTO userDTO);

    /**
     * 删除用户
     */
    void delete(Long id);
}
