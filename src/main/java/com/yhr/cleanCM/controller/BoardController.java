package com.yhr.cleanCM.controller;

import com.yhr.cleanCM.dto.board.BoardSaveForm;
import com.yhr.cleanCM.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards/add")
    public String showAddBoard(Model model){

        model.addAttribute("boardSaveForm", new BoardSaveForm());

        return "usr/board/add";

    }

    @PostMapping("/boards/add")
    public String doAddBoard(BoardSaveForm boardSaveForm){

        boardService.save(boardSaveForm);

        return "redirect:/";
    }

}
