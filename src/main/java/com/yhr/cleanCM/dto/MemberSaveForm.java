package com.yhr.cleanCM.dto;

import lombok.Data;

@Data
public class MemberSaveForm {

    private String loginId;
    private String loginPw;
    private String name;
    private String nickname;
    private String email;

}
