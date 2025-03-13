package edu.hpu.usercenter.service;

import edu.hpu.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiangyouxiu
 * @description 针对表【user】的数据库操作Service
 * @createDate 2025-03-10 19:54:49
 */
public interface UserService extends IService<User> {
    /**
     * 用户注释
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 检验密码
     * @return 新用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword,String stuId);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @Param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSafetyUser(User user);

    /**
     * 注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    boolean containsNullCharacter(String... values);
}
