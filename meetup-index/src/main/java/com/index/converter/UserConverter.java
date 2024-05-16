package com.index.converter;

import com.index.entity.User;
import com.index.entity.nested.NestedUser;
import com.index.model.UserDTO;

/**
 * @author David Sapozhnik
 */
public final class UserConverter {

    private UserConverter() {
    }

    public static UserDTO toUserDTO(User user) {
        var userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhone(user.getPhone());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static NestedUser toUser(UserDTO userDTO) {
        var user = new NestedUser();
        user.setUserId(userDTO.getUserId());
        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
