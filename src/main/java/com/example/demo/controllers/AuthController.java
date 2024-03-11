package com.example.demo.controllers;

import com.example.demo.models.AccountDto;
import com.example.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.demo.constants.Constants.Numbers.EXPIRATION_TIME;
import static com.example.demo.constants.Constants.Strings.SAT_COOKIE;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/registerGet")
    public String registerGet(Model model) {

        model.addAttribute("accountDto", new AccountDto());

        return "register-and-authorise";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute("accountDto") AccountDto accountDto) {

        var authToken = authService.register(accountDto);

        var cookie = ResponseCookie.from(SAT_COOKIE, authToken)
                .httpOnly(true)
                .secure(true)
                .maxAge(EXPIRATION_TIME / 1000)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("<script>window.location.href='/main-page';</script>");
    }

    @GetMapping("/authoriseGet")
    public String authoriseGet(Model model) {

        model.addAttribute("authoriseAccountDto", new AccountDto());

        return "login";
    }

    @PostMapping("/authorise")
    public ResponseEntity<String> authorise(@ModelAttribute("authoriseAccountDto") AccountDto authoriseAccountDto) {

        var authToken = authService.authorise(authoriseAccountDto);

        var cookie = ResponseCookie.from(SAT_COOKIE, authToken)
                .httpOnly(true)
                .secure(true)
                .maxAge(EXPIRATION_TIME / 1000)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("<script>window.location.href='/main-page';</script>");
    }
}
