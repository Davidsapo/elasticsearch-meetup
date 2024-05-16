package com.auth.repository;

import com.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author David Sapozhnik
 */
public interface UserRepository extends JpaRepository<User, UUID> {
}
