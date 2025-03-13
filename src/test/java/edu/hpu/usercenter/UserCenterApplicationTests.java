package edu.hpu.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {
    @Test
    void testDigest() throws NoSuchAlgorithmException {
//        MessageDigest md5 = MessageDigest.getInstance("MD5");
//        byte[] bytes = md5.digest("abcd".getBytes(StandardCharsets.UTF_8));
        String s1 = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
//        String s = new String(bytes);
        System.out.println(s1);
    }
    @Test
    void contextLoads() {
    }

}
