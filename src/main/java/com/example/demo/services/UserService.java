package com.example.demo.services;

import com.example.demo.models.UserDto;

public interface UserService {

    UserDto getUserById(String userId);

    void updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);

}
