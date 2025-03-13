package edu.hpu.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hpu.usercenter.common.ErrorCode;
import edu.hpu.usercenter.exception.BusinessException;
import edu.hpu.usercenter.model.User;
import edu.hpu.usercenter.service.UserService;
import edu.hpu.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.hpu.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * @author xiangyouxiu
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2025-03-10 19:54:49
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper usermapper;
    /**
     * 盐
     */
    private static final String SALT = "xiangyang";


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String stuId) {
        //1、校验

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,stuId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }

        if (stuId.length() < 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学号长度过短");
        }

        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能包含特殊字符");
        }
        //校验密码和密码是否相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已经存在");
        }

        // 学号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stuId", stuId);
        count = usermapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学号重复");
        }


        //2、对密码进行加密

        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //3、向数据库中插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        user.setStuId(stuId);

        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "注册失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1、校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 6) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }

        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        //2、对密码进行加密

        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", newPassword);
        User user = usermapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("user login failes, userAccount cannot match userPassword");
            return null;
        }

        //用户脱敏
        User safetyUser = getSafetyUser(user);

        //记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        //返回脱敏后的用户信息
        return safetyUser;
    }

    @Override
    public User getSafetyUser(User user) {
        if(user == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setStatus(user.getStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setStuId(user.getStuId());
        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        /**
         * 移除用户态
         */
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
    @Override
    public boolean containsNullCharacter(String... values) {
        for (String value : values) {
            if (value != null && value.contains("\0")) {
                return true;
            }
        }
        return false;
    }
}




