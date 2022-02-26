package com.yothinix.ecommerce.users.repository;

import com.yothinix.ecommerce.users.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
}
