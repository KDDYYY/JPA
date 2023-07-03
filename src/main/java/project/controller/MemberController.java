package project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import project.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/home")
    public String main(){
        return "main";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "practice/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "practice/register";
    }

    @PostMapping("/register")
    public String register(@Valid MemberForm form, BindingResult result,Model model) {

        if (result.hasErrors()) {
            return "practice/register";
        }
            Member member = new Member();
            member.setName(form.getName());
            member.setEmail(form.getEmail());
            member.setPw(form.getPw());

            memberService.join(member);
            return "redirect:/login";
    }

    @PostMapping("/login")
    public String signin(@Valid @ModelAttribute MemberForm form, HttpServletRequest request){
       try {
           Member member = new Member();
           member.setEmail(form.getEmail());
           member.setPw(form.getPw());

           Member loginResult = memberService.login(member);

           if (loginResult == null) {
               //login 실패
               return "practice/login";
           }
           //성공
           request.getSession().setAttribute("me", member);
           return "redirect:/home";

           //login 실패
       }catch (DataAccessException e){
           return "practice/login";
       }
    }

    //로그아웃
   @GetMapping("/logout")
    public String signout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/home";
    }


    //회원탈퇴
    @GetMapping("/withdraw")
    public String withdraw(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        session.invalidate();
        return "redirect:/home";

    }




}




