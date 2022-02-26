package com.yothinix.ecommerce.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserAddressRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserAddressRepository userAddressRepository;

    @Test
    void findByIdShouldReturnUserAddressTest() {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(1);
        userAddress.setUserId(1);
        userAddress.setEmail("email@email.com");
        userAddress.setName("Human test");
        userAddress.setAddress("12/34");
        userAddress.setPostalCode("10110");
        userAddress.setDistrict("Chatuchak");
        userAddress.setProvince("Bangkok");
        userAddress.setTelephone("0812345678");
        testEntityManager.persist(userAddress);

        Optional<UserAddress> userAddressOptional = userAddressRepository.findById(1);
        UserAddress actual = userAddressOptional.get();

        assertEquals(1, actual.getId());
        assertEquals(1, actual.getUserId());
        assertEquals("email@email.com", actual.getEmail());
        assertEquals("Human test", actual.getName());
        assertEquals("12/34", actual.getAddress());
        assertEquals("10110", actual.getPostalCode());
        assertEquals("Chatuchak", actual.getDistrict());
        assertEquals("Bangkok", actual.getProvince());
        assertEquals("0812345678", actual.getTelephone());
    }

    @Test
    void findByIdShouldReturnEmptyWhenUserAddressNotFoundTest() {
        Optional<UserAddress> actual = userAddressRepository.findById(1);

        assertTrue(actual.isEmpty());
    }
}