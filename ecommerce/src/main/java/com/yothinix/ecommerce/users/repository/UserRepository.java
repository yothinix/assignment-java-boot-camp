package com.yothinix.ecommerce.users.repository;

import com.yothinix.ecommerce.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
