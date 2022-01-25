package com.yhr.cleanCM.controller;

import com.yhr.cleanCM.domain.Board;
import com.yhr.cleanCM.dto.board.BoardDTO;
import com.yhr.cleanCM.dto.board.BoardModifyForm;
import com.yhr.cleanCM.dto.board.BoardSaveForm;
import com.yhr.cleanCM.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards/add")
    public String showAddBoard(Model model) {

        model.addAttribute("boardSaveForm", new BoardSaveForm());

        return "adm/board/add";

    }

    @PostMapping("/boards/add")
    public String doAddBoard(BoardSaveForm boardSaveForm) {

        boardService.save(boardSaveForm);

        return "redirect:/";
    }

    // 게시판 리스트
    @GetMapping("/boards")
    public String showBoardList(Model model) {

        List<Board> boardList = boardService.findAll();

        model.addAttribute("boardList", boardList);

        return "adm/board/list";

    }

    @GetMapping("/boards/{id}")
    public String showBoardDetail(@PathVariable(name = "id") Long id, Model model) {

        try {

            BoardDTO boardDetail = boardService.getBoardDetail(id);
            model.addAttribute("board", boardDetail);

        } catch (Exception e) {
            return "redirect:/";
        }

        return "adm/board/detail";

    }

    @GetMapping("/boards/modify")
    public String showModifyBoard(Model model) {

        model.addAttribute("boardModifyForm", new BoardModifyForm());

        return "adm/board/modify";
    }

    @PostMapping("/boards/modify")
    public String doModifyBoard(BoardModifyForm boardModifyForm) {

        try {
            boardService.modify(boardModifyForm);
        } catch (Exception e) {
            return "adm/board/modify";
        }

        return "redirect:/";
    }


}
