package dev.hoon.basic.domain.account.dto;

import dev.hoon.basic.domain.account.meta.SexType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryAccount {

    @Pattern(regexp = "^[가-힣A-Za-z]{1,20}$")
    private String name;

    @Pattern(regexp = "^[a-z]{1,30}$")
    private String nickName;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()\\[\\]{}\\\\~`])[A-Za-z\\d!@#$%^&*()\\[\\]{}\\\\~`]{10,100}$")
    private String password;

    @NotNull
    private Integer countryCode;

    @Pattern(regexp = "^[0-9]{10,20}$")
    private String phone;

    @Pattern(regexp = "^[0-9a-zA-Z-_.+]+@[0-9a-zA-Z-_.]+\\.[a-zA-Z]{2,3}$")
    private String email;

    @NotNull
    private SexType sex;
}
