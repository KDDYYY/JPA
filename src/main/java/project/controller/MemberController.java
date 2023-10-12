package project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import project.domain.Board;
import project.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.domain.Message;
import project.service.BoardService;
import project.service.MemberService;
import project.service.MessageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final BoardService boardService;

    private final MessageService messageService;

    //내 정보
    @GetMapping("/members/myInfo")
    public String myInfo(Model model, HttpSession session){
        Member sessionMember = (Member) session.getAttribute("member");
        Member member = memberService.findOne(sessionMember.getId());

        List<Board> myBoards = boardService.findMyBoards(member.getId());
        List<Message> sendMessage = messageService.findSendMessage(member.getId());
        List<Message> receiveMessage = messageService.findReceiveMessage(member.getId());

        model.addAttribute("pt", member.getPt());
        model.addAttribute("myBoards", myBoards);
        model.addAttribute("sendMessage", sendMessage);
        model.addAttribute("receiveMessage", receiveMessage);
        return "practice/myInfo";
    }


    //회원가입
    @GetMapping("/members/register")
    public String signUpForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "practice/register";
    }

    //회원가입
    @PostMapping("/members/register")
    public String signUp(@Valid MemberForm form, BindingResult result) {
    try {
        if (result.hasErrors()) {
            return "practice/register";
        }

        Member member = new Member(form.getName(), form.getName(), form.getPw());

        memberService.join(member);
        return "redirect:/members/login";
    }catch (DataAccessException e){
        return "practice/register";
        }
    }

    //로그인
    @GetMapping("/members/login")
    public String signInForm(Model model){
        //model.addAttribute("memberForm", new MemberForm());
        return "practice/login";
    }

    //로그인
    @PostMapping("/members/login")
    public String signIn(@Valid @ModelAttribute MemberForm form, HttpSession session){
       try {
           Member member = memberService.findByEmail(form.getEmail());
           if(member == null || (!member.getPw().equals(form.getPw()))){
               return "practice/login";
           }

           //성공
           session.setAttribute("member", member);

           return "redirect:/home";

           //login 실패
       }catch (DataAccessException e){
           return "practice/login";
       }
    }

    //로그아웃
   @GetMapping("/members/logout")
    public String signOut(HttpSession session){
        session.invalidate();
        return "redirect:/home";
    }


    //회원조회
    @GetMapping("/members/{id}")
    public String viewMember(@PathVariable Long id, Model model){
        Member member = memberService.findOne(id);
        List<Board> memberBoard = memberService.memberBoard(id);
        model.addAttribute("member",member);
        model.addAttribute("memberBoard", memberBoard);
        return "practice/info";
    }



    //회원탈퇴
    @GetMapping("/members/withdraw")
    public String withdraw(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        session.invalidate();
        return "redirect:/home";
    }




}




