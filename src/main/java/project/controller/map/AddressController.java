package project.controller.map;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.domain.Address;
import project.domain.Member;
import project.service.AddressService;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    @GetMapping("/map")
    public String showMap() {
        return "practice/map";
    }

    //주소 저장
    @PostMapping("/address/register")
    public String registerAddress(@RequestBody Address address, HttpSession session) {
        try {
            Member sessionMember = (Member) session.getAttribute("member");

            if (sessionMember == null) {
                return "practice/register";
            }else if(address == null){
                return "practice/map";
            }

            address.setMember(sessionMember);

            addressService.saveAddress(address);

            return "redirect:/home";
        } catch (DataAccessException e) {
            return "practice/home";
        }
    }
}
