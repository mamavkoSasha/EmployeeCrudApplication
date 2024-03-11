package com.example.demo.services.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.AccountDto;
import com.example.demo.rep.UserRepos;
import com.example.demo.services.AuthService;
import com.example.demo.utils.AuthTokenUtils;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo.constants.Constants.Messages.FAILED_AUTHORISATION;
import static com.example.demo.constants.Constants.Messages.INVALID_REGISTER_INFO;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepos userRepository;
    private final AuthTokenUtils authTokenUtils;

    @Override
    public String register(AccountDto registerAccountDto) {

        var username = Optional.ofNullable(registerAccountDto.getUsername())
                .orElseThrow(() -> new RestException(INVALID_REGISTER_INFO, HttpStatus.BAD_REQUEST));

        var email = Optional.ofNullable(registerAccountDto.getEmail())
                .orElseThrow(() -> new RestException(INVALID_REGISTER_INFO, HttpStatus.BAD_REQUEST));

        var encodedPassword = Optional.ofNullable(registerAccountDto.getPassword())
                .map(String::new)
                .map(passwordString -> BCrypt.hashpw(passwordString, BCrypt.gensalt()))
                .orElseThrow(() -> new RestException(INVALID_REGISTER_INFO, HttpStatus.BAD_REQUEST));

        var userId = Optional.ofNullable(registerAccountDto.getAccountType())
                .map(accountType -> switch (accountType) {
                    case USER -> userRepository.registerUser(username, email, encodedPassword);

                    default -> throw new IllegalStateException("Unexpected value: " + accountType);
                }).orElseThrow(() -> new RestException(INVALID_REGISTER_INFO, HttpStatus.BAD_REQUEST));

        return authTokenUtils.generateToken(registerAccountDto.getAccountType(), userId);
    }

    @Override
    public String authorise(AccountDto authoriseAccountDto) {

        var userDto = Optional.ofNullable(authoriseAccountDto.getUsername())
                .flatMap(userRepository::gerUserByUsername)
                .or(() -> Optional.ofNullable(authoriseAccountDto.getEmail())
                        .flatMap(userRepository::getUserByEmail))
                .orElseThrow(() -> new RestException(FAILED_AUTHORISATION, HttpStatus.UNAUTHORIZED));

        Optional.ofNullable(authoriseAccountDto.getPassword())
                .map(String::new)
                .filter(passwordString -> BCrypt.checkpw(passwordString, userDto.getPassword()))
                .orElseThrow(() -> new RestException(FAILED_AUTHORISATION, HttpStatus.UNAUTHORIZED));

        return authTokenUtils.generateToken(authoriseAccountDto.getAccountType(), userDto.getUserId());
    }
}
