package com.index.controller;

import com.index.enums.UserSort;
import com.index.service.UserService;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public List<UUID> search(@RequestParam(required = false) String search,
                             @RequestParam(required = false) UserSort sort,
                             @RequestParam(required = false) SortOrder sortOrder) {

        return userService.searchUsers(search, sort, sortOrder);
    }
}
