package edu.hpu.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 3115134150987319831L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String stuId;
}
