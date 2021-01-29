package dev.hoon.basic.domain.account.controller;

import dev.hoon.basic.domain.account.dto.RegistryAccount;
import dev.hoon.basic.domain.account.dto.SignIn;
import dev.hoon.basic.domain.account.model.Account;
import dev.hoon.basic.domain.account.service.AccountService;
import dev.hoon.basic.global.config.security.JwtTokenProvider;
import dev.hoon.basic.global.util.MobileNumberUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AccountService   accountService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginController(AccountService accountService, JwtTokenProvider jwtTokenProvider) {

        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign-In", description = "Sign-In by Name, Password")
    private ResponseEntity<?> signIn(@Valid @RequestBody SignIn signIn) throws Exception {

        logger.debug("sign-in >> name : {}", signIn.getName());

        return accountService.isValidSignIn(signIn) ?
                ResponseEntity.ok()
                        .header("Authentication",
                                jwtTokenProvider.generateToken(signIn.getName())).build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Create Account", description = "Create Account")
    private ResponseEntity<Long> create(@Valid @RequestBody RegistryAccount accountDto) throws Exception {

        if (!MobileNumberUtil.validPhoneNumber(accountDto.getCountryCode(), accountDto.getPhone())) {
            return ResponseEntity.badRequest().body(0L);
        }

        Account account = accountService.create(accountDto);

        logger.debug("create >> id : {}", account.getId());

        return ResponseEntity.ok(account.getId());
    }

}
