package edu.hpu.usercenter.service;
import java.util.Date;

import edu.hpu.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    public void testAdduser(){
        User user = new User();
        user.setUsername("xiangyang");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertEquals(true,result);


    }

    @Test
    void userRegister() {
        String userAccount = "xiangyang1";
        String userPassword = "";
        String checkPassword = "12345678";
        String stuId = "312109010211";
        long result = userService.userRegister(userAccount, userPassword, checkPassword,stuId);
        Assertions.assertEquals(-1,result);

        userAccount = "xiang";
        result = userService.userRegister(userAccount, userPassword, checkPassword,stuId);
        Assertions.assertEquals(-1,result);

        userAccount = "xiangyang1";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword,stuId);
        Assertions.assertEquals(-1,result);

        userAccount = "xiang yang1";
        result = userService.userRegister(userAccount, userPassword, checkPassword,stuId);
        Assertions.assertEquals(-1,result);


        userAccount = "xiangyang";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,stuId);
        Assertions.assertEquals(-1,result);






    }
}