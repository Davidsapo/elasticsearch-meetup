package com.auth.service;

import com.auth.client.IndexClient;
import com.auth.client.PurchaseClient;
import com.auth.entity.User;
import com.auth.enums.UserSort;
import com.auth.model.OrderDTO;
import com.auth.model.UserDTO;
import com.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.auth.enums.OrderStatus.PAID;
import static java.util.Comparator.comparingInt;
import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * User Service.
 *
 * @author David Sapozhnik
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IndexClient indexClient;

    @Autowired
    private PurchaseClient purchaseClient;

    /**
     * Register user user dto.
     *
     * @param userDTO the user dto
     * @return the user dto
     */
    public UserDTO registerUser(UserDTO userDTO) {
        var user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        copyProperties(user, userDTO);
        return userDTO;
    }

    /**
     * Update user user dto.
     *
     * @param userId  the user id
     * @param userDTO the user dto
     * @return the user dto
     */
    @Transactional
    public UserDTO updateUser(UUID userId, UserDTO userDTO) {
        var user = userRepository.getReferenceById(userId);
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
        copyProperties(user, userDTO);
        return userDTO;
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    var userDTO = new UserDTO();
                    copyProperties(user, userDTO);
                    return userDTO;
                })
                .toList();
    }

    /**
     * Search users.
     *
     * @return the all users
     */
    public List<UserDTO> searchUsers(String search, UserSort sort, Direction direction) {

        var userIds = indexClient.search(search, sort, direction);

        var users = userRepository.findAllById(userIds).stream()
                .map(user -> {
                    var userDTO = new UserDTO();
                    copyProperties(user, userDTO);
                    return userDTO;
                })
                .toList();

        for (var user : users) {
            var orders = purchaseClient.getOrders(user.getUserId());
            var amountSpent = orders.stream()
                    .filter(order -> order.getStatus().equals(PAID))
                    .mapToDouble(OrderDTO::getAmountPaid)
                    .sum();
            user.setAmountSpent(amountSpent);
        }

        return users.stream().sorted(comparingInt(user -> userIds.indexOf(user.getUserId()))).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getUser(UUID userId) {
        var user = userRepository.getById(userId);
        var userDTO = new UserDTO();
        copyProperties(user, userDTO);
        return userDTO;
    }
}
