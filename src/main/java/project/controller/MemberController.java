package project.controller;

import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @GetMapping("/members/myinfo")
    public String myInfo(){
        return "myinfo";
    }


    @GetMapping("/members/register")
    public String registerForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "practice/register";
    }

    @PostMapping("/members/register")
    public String register(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "practice/register";
        }
            Member member = new Member();
            member.setName(form.getName());
            member.setEmail(form.getEmail());
            member.setPw(form.getPw());

            memberService.join(member);
            return "redirect:/members/login";
    }

    @GetMapping("/members/login")
    public String loginForm(Model model){
        //model.addAttribute("memberForm", new MemberForm());
        return "practice/login";
    }

    @PostMapping("/members/login")
    public String signIn(@Valid @ModelAttribute MemberForm form, HttpSession session, Model model){
       try {
           Member member = new Member();
           member.setEmail(form.getEmail());
           member.setPw(form.getPw());
           member.setName(memberService.nameReturn(form.getEmail()));

           Member loginResult = memberService.login(member);

           if (loginResult == null) {
               //login 실패
               return "practice/login";
           }

           //성공
           session.setAttribute("member", member);
           //model.addAttribute("member", member);
           return "redirect:/home";

           //login 실패
       }catch (DataAccessException e){
           //attributes.addFlashAttribute("message")
           return "practice/login";
       }

    }

    //로그아웃
   @GetMapping("/members/logout")
    public String signOut(HttpSession session){
        session.invalidate();
        return "redirect:/home";
    }


    //회원탈퇴
    @GetMapping("/members/withdraw")
    public String withdraw(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        session.invalidate();
        return "redirect:/home";
    }




}




