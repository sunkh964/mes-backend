package com.example.mes_backend.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "아이디는 필수 사항입니다.")
    private String employeeId;

    @NotBlank(message = "비밀번호는 필수 사항입니다.")
    private String password;
}