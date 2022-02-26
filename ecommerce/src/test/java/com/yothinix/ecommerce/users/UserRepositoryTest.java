package com.yothinix.ecommerce.users;

import com.yothinix.ecommerce.users.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByIdShouldReturnUserTest() {
        User user = new User(1, "yothinix");
        testEntityManager.persist(user);

        Optional<User> userOptional = userRepository.findById(1);
        User actual = userOptional.get();

        assertEquals(1, actual.getId());
        assertEquals("yothinix", actual.getUsername());
    }

    @Test
    void findByIdShouldReturnEmptyWhenUserNotFoundTest() {
        Optional<User> actual = userRepository.findById(1);

        assertTrue(actual.isEmpty());
    }
}