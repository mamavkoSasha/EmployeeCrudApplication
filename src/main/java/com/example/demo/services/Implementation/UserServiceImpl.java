package com.example.demo.services.Implementation;

import com.example.demo.exception.RestException;
import com.example.demo.models.UserDto;
import com.example.demo.rep.UserRepos;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo.constants.Constants.Messages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepos userRepository;


    @Override
    public UserDto getUserById(String userId) {

        Optional<UserDto> userDtoOptional = userRepository.getUserById(userId);

        if (userDtoOptional.isEmpty()) {
            throw new RestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return userDtoOptional.get();
    }

    @Override
    public void updateUser(UserDto userDto, String userId) {

        Optional<UserDto> userDtoOptional = userRepository.getUserById(userId);

        if (userDtoOptional.isEmpty()) {
            throw new RestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        userRepository.updateUser(userDto, userId);
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserDto> userDtoOptional = userRepository.getUserById(userId);

        if (userDtoOptional.isEmpty()) {
            throw new RestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        userRepository.deleteUser(userId);
    }
}
