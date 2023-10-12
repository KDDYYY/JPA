package project.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    //@NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    @NotEmpty(message = "회원 이메일은 필수 입니다.")
    private String email;

    @NotNull(message = "회원 비밀번호는 필수 입니다.")
    private Long pw;

}
