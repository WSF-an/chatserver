package org.example.chatserver.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegister implements Serializable {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

}
