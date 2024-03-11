package com.example.demo.rep;

import com.example.demo.models.UserDto;

import java.util.Optional;

public interface UserRepos {

    String registerUser(String username, String email, String encodedPassword);

    Optional<UserDto> gerUserByUsername(String username);

    Optional<UserDto> getUserByEmail(String email);

    Optional<UserDto> getUserById(String userId);

    void updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

}
