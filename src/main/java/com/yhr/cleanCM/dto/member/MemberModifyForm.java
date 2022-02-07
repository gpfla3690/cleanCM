package com.yhr.cleanCM.dto.member;

import com.yhr.cleanCM.domain.Member;
import lombok.Data;

@Data
public class MemberModifyForm {

    private Long id;

    private String loginId;

    private String loginPw;

    private String name;

    private String nickname;

    private String email;

    public MemberModifyForm(Member member) {

        this.id = member.getId();

        this.loginId = member.getLoginId();
        this.loginPw = member.getLoginPw();

        this.name = member.getName();

        this.nickname = member.getNickname();

        this.email = member.getEmail();

    }
}
