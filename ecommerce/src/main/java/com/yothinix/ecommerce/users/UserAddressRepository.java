package com.yothinix.ecommerce.users;

import com.yothinix.ecommerce.users.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
}
