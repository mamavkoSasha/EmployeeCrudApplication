package com.example.demo.interceptor;

import com.example.demo.enums.AccountType;
import com.example.demo.exception.RestException;
import com.example.demo.models.ValidationResult;
import com.example.demo.utils.AuthTokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.demo.constants.Constants.Messages.UNAUTHORISED_USER_REQUEST;
import static com.example.demo.constants.Constants.Numbers.EXPIRATION_TIME;
import static com.example.demo.constants.Constants.Strings.SAT_COOKIE;
import static com.example.demo.constants.Constants.Strings.USER_ID;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!test")
public class AuthHandlerInterceptor implements HandlerInterceptor {

    private final AuthTokenUtils authTokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        final var cookies = Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Stream::of)
                .collect(Collectors.toMap(Cookie::getName,
                        Function.identity(),
                        (currentValue, newValue) -> newValue,
                        () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER))
                );

        var validationResult = Optional.ofNullable(cookies.get(SAT_COOKIE))
                .map(Cookie::getValue)
                .filter(token -> !token.isEmpty())
                .map(authTokenUtils::validateToken)
                .orElseThrow(() -> new RestException(UNAUTHORISED_USER_REQUEST, HttpStatus.UNAUTHORIZED));

        Optional.of(validationResult)
                .filter(ValidationResult::isTokenValid)
                .orElseThrow(() -> new RestException(UNAUTHORISED_USER_REQUEST, HttpStatus.UNAUTHORIZED));

        var accountType = validationResult.getAccountType();
        var accountId = validationResult.getAccountId();

        if (accountType == AccountType.USER) {
            request.setAttribute(USER_ID, accountId);
        }

        var renewedToken = authTokenUtils.generateToken(accountType, accountId);
        var cookie = ResponseCookie.from(SAT_COOKIE, renewedToken)
                .httpOnly(true)
                .secure(true)
                .maxAge(EXPIRATION_TIME / 1000)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return true;
    }
}
