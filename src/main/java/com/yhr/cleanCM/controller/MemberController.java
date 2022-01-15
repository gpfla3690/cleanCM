package com.yhr.cleanCM.controller;

import com.yhr.cleanCM.dto.MemberSaveForm;
import com.yhr.cleanCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/join")
    public String showSignup(Model model) {

        model.addAttribute("memberSaveForm", new MemberSaveForm());
        return "usr/member/join";

    }

    @PostMapping("/members/join")
    public String doSignup(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult, Model model) {

        if ( bindingResult.hasErrors() ) {
            return "usr/member/join";
        }

        try {

            memberService.save(memberSaveForm);

        } catch ( Exception e ) {

            model.addAttribute("err_msg", e.getMessage());

            return "usr/member/signup";

        }

        return "redirect:/";

    }

}
