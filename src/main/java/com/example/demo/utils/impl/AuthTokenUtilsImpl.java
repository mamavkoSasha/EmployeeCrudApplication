package com.example.demo.utils.impl;

import com.example.demo.enums.AccountType;
import com.example.demo.exception.RestException;
import com.example.demo.models.ValidationResult;
import com.example.demo.utils.AuthTokenUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.example.demo.constants.Constants.Messages.UNAUTHORISED_USER_REQUEST;
import static com.example.demo.constants.Constants.Numbers.EXPIRATION_TIME;
import static com.example.demo.constants.Constants.Strings.ACCOUNT_TYPE_PROPERTY;
import static com.example.demo.constants.Constants.Strings.ALGORITHM_PROPERTY;
import static com.example.demo.constants.Constants.Strings.ENCODING_ALGORITHM;
import static com.example.demo.constants.Constants.Strings.EXPIRATION_TIMESTAMP_PROPERTY;
import static com.example.demo.constants.Constants.Strings.ISSUED_TIMESTAMP_PROPERTY;
import static com.example.demo.constants.Constants.Strings.SUBJECT_PROPERTY;

@Component
@RequiredArgsConstructor
public class AuthTokenUtilsImpl implements AuthTokenUtils {

    @Value("${auth.encode-algorithm-key}")
    private String secretKey;

    private static final Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public String generateToken(AccountType accountType, String accountId) {

        var claimsMap = Map.of(
                ACCOUNT_TYPE_PROPERTY, accountType.name()
        );

        return Jwts.builder()
                .claims(claimsMap)
                .subject(accountId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    @Override
    public ValidationResult validateToken(String token) {

        var tokenClaims = Try.of(() -> Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .build()
                        .parseSignedClaims(token))
                .onFailure(throwable -> {
                    throw new RestException(UNAUTHORISED_USER_REQUEST, HttpStatus.UNAUTHORIZED);
                }).get();

        var validationResultBuilder = ValidationResult.builder();

        var isTokenValid = Optional.ofNullable(tokenClaims)
                .filter(claims -> {

                    var isHeaderValid = Optional.ofNullable(claims.getHeader())
                            .map(claimsHeaders -> claimsHeaders.get(ALGORITHM_PROPERTY))
                            .map(String::valueOf)
                            .filter(encodingAlgorithm -> encodingAlgorithm.equalsIgnoreCase(ENCODING_ALGORITHM))
                            .isPresent();

                    var isPayloadValid = Optional.ofNullable(claims.getPayload())
                            .filter(payload -> {

                                var isAccountTypeValid = Optional.ofNullable(payload.get(ACCOUNT_TYPE_PROPERTY))
                                        .map(String::valueOf)
                                        .map(value -> {
                                            var accountType = AccountType.find(value);

                                            validationResultBuilder.accountType(accountType);

                                            return accountType;
                                        })
                                        .isPresent();

                                var isSubjectValid = Optional.ofNullable(payload.get(SUBJECT_PROPERTY))
                                        .map(String::valueOf)
                                        .filter(subject -> {
                                            validationResultBuilder.accountId(subject);

                                            return UUID_REGEX_PATTERN.matcher(subject).matches();
                                        })
                                        .isPresent();

                                var isIssuedDateValid = Optional.ofNullable(payload.get(ISSUED_TIMESTAMP_PROPERTY))
                                        .map(String::valueOf)
                                        .map(Long::parseLong)
                                        .filter(issuedDate -> issuedDate < System.currentTimeMillis())
                                        .isPresent();

                                var isExpirationDateValid = Optional.ofNullable(payload.get(EXPIRATION_TIMESTAMP_PROPERTY))
                                        .map(String::valueOf)
                                        .map(Long::parseLong)
                                        .map(Date::new)
                                        .filter(expirationDate -> {
                                            var currentDateInSeconds = new Date(System.currentTimeMillis() / 1000);
                                            return expirationDate.after(currentDateInSeconds);
                                        })
                                        .isPresent();

                                return isAccountTypeValid && isSubjectValid && isIssuedDateValid && isExpirationDateValid;
                            }).isPresent();

                    return isHeaderValid && isPayloadValid;
                }).isPresent();

        return validationResultBuilder.isTokenValid(isTokenValid)
                .build();
    }
}
