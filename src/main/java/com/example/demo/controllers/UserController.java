package com.example.demo.controllers;

import com.example.demo.models.UserDto;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.constants.Constants.Strings.USER_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public UserDto getUserById(@Parameter(hidden = true) @RequestAttribute(USER_ID) String userId) {

        return userService.getUserById(userId);
    }

    @PutMapping("/update")
    public void updateUser(@Parameter(hidden = true) @RequestAttribute(USER_ID) String userId,
                           @RequestBody UserDto userDto) {

        userService.updateUser(userDto, userId);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@Parameter(hidden = true) @RequestAttribute(USER_ID) String userId) {

        userService.deleteUser(userId);
    }
}
