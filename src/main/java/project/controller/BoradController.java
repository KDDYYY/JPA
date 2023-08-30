package project.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.domain.Board;
import project.domain.Member;
import project.service.BoardService;
import project.service.MemberService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoradController {

    private final BoardService boardService;

    private final MemberService memberService;

    @GetMapping("/home")
    public String main(Model model){
        List<Board> boards = boardService.findBoards();
        model.addAttribute("boards", boards);
        return "practice/home";
    }

//    @GetMapping("/home")
//    public String main(Model model){
//        List<Board> boards = boardService.findBoards();
//        model.addAttribute("boards", boards);
//        return "main";
//    }


    @GetMapping("/boards/new")
    public String createBoard(Model model, HttpSession session){
        Member member = (Member) session.getAttribute("member");

        if(member != null) {
            model.addAttribute("BoardForm", new BoardForm());
            return "practice/board";

        }else {
            return "redirect:/members/login";
        }
    }

    @PostMapping("/boards/new")
    public String create(@Valid BoardForm boardForm, HttpSession session){
        try {
            Member sessionMember = (Member) session.getAttribute("member");

            Member member = memberService.findByEmail(sessionMember.getEmail());

            if(sessionMember != null) {

                Board board = new Board(boardForm.getTitle(), boardForm.getContent(), LocalDateTime.now());
                boardService.saveBoard(board, member);

                return "redirect:/home";

            }else {
                return "redirect:/members/login";
            }
        }catch (Exception e){
            return "redirect:/members/login";
        }

    }

    @GetMapping("/boards/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boardService.findOne(id);
        model.addAttribute("board", board);
        return "practice/boardView"; // 상세 조회 페이지의 Thymeleaf 템플릿 이름
    }

    @GetMapping("/members/{id}")
    public String viewMember(@PathVariable Long id, Model model){
        Member member = memberService.findOne(id);
        model.addAttribute("member",member);
        return "practice/info";
    }

}
