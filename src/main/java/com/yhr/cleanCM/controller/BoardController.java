package com.yhr.cleanCM.controller;

import com.yhr.cleanCM.domain.Board;
import com.yhr.cleanCM.domain.Member;
import com.yhr.cleanCM.dto.board.BoardDTO;
import com.yhr.cleanCM.dto.board.BoardModifyForm;
import com.yhr.cleanCM.dto.board.BoardSaveForm;
import com.yhr.cleanCM.service.BoardService;
import com.yhr.cleanCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/adm/boards/add")
    public String showAddBoard(Model model) {

        model.addAttribute("boardSaveForm", new BoardSaveForm());

        return "adm/board/add";

    }

    @PostMapping("/adm/boards/add")
    public String doAddBoard(@Validated BoardSaveForm boardSaveForm, BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()){
            return "adm/board/add";
        }

        Member findAdmin = memberService.findByLoginId(principal.getName());

        boardService.save(boardSaveForm, findAdmin);

        return "redirect:/boards";
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

    @GetMapping("/adm/boards/modify/{id}")
    public String showModifyBoard(@PathVariable(name = "id")Long id, Model model) {

        try {

            BoardDTO board = boardService.getBoardDetail(id);

            model.addAttribute("board", new BoardModifyForm(
                    board.getId(),
                    board.getName(),
                    board.getDetail()
            ));

            return "adm/board/modify";
        }catch (Exception e){
            return "redirect:/";
        }
    }

    @PostMapping("/adm/boards/modify/{id}")
    public String doModifyBoard(@PathVariable(name = "id") Long id, BoardModifyForm boardModifyForm) {

        try {
            boardService.modify(id, boardModifyForm);
        } catch (Exception e) {
            return "adm/board/modify";
        }

        return "redirect:/";
    }

    @GetMapping("/adm/boards/delete/{id}")
    public String doDeleteBoard(@PathVariable(name = "id") Long id) {

        try {
            boardService.delete(id);
            return "adm/board/list";
        } catch (Exception e) {
            return "adm/board/list";
        }

    }


}
