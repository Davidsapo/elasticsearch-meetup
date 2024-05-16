package com.auth.controller;

import com.auth.enums.UserSort;
import com.auth.model.UserDTO;
import com.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * User Controller.
 *
 * @author David Sapozhnik
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable UUID userId, @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam(required = false) String search,
                                     @RequestParam(required = false) UserSort sort,
                                     @RequestParam(required = false) Direction direction) {

        return userService.searchUsers(search, sort, direction);
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }
}
