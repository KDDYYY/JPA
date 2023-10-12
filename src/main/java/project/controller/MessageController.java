package project.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.domain.Board;
import project.domain.Member;
import project.domain.Message;
import project.service.BoardService;
import project.service.MemberService;
import project.service.MessageService;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final MemberService memberService;

    private final BoardService boardService;

    @GetMapping("/message/{id}")
    public String sendMessageForm(@PathVariable("id") Long id, Model model, HttpSession session){
        Member sessionMember = (Member) session.getAttribute("member");

        Member receiveMember = memberService.findOne(id);

        if(sessionMember == null)
            return "practice/login";

        model.addAttribute("member", receiveMember);
        model.addAttribute("MessageForm", new MessageForm());
        return "practice/sendMessage";
    }

    @PostMapping("/message/{id}")
    public String sendMessage(@PathVariable("id") Long id, HttpSession session, @Valid MessageForm messageForm){
        try {
            Member sessionMember = (Member) session.getAttribute("member");
            Member sendMember = memberService.findOne(sessionMember.getId());

            Member receiveMember = memberService.findOne(id);

            Message message = new Message(messageForm.getTitle(), messageForm.getContent(), sendMember, receiveMember);

            messageService.saveMessage(message, sendMember, receiveMember);

            System.out.println(sendMember + "----------------------------------------------");
            System.out.println(receiveMember + "----------------------------------------------");

            return "redirect:/members/myInfo";
        }
        catch (Exception e){
            return "redirect:/members/" + id;
        }
    }


}
