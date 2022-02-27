package com.yothinix.ecommerce.users;

import com.yothinix.ecommerce.users.entity.User;
import com.yothinix.ecommerce.users.entity.UserAddress;
import com.yothinix.ecommerce.users.repository.UserAddressRepository;
import com.yothinix.ecommerce.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    UserAddressRepository userAddressRepository;

    @MockBean
    UserRepository userRepository;

    @Test
    void createUserAddressSuccessTest() {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(1);
        userAddress.setUserId(1);
        userAddress.setEmail("human@email.com");
        userAddress.setName("Human address");
        userAddress.setAddress("12/34 example road");
        userAddress.setPostalCode("10110");
        userAddress.setDistrict("Chatuchak");
        userAddress.setProvince("Krung Thep Maha Nakhon");
        userAddress.setTelephone("0812345678");
        when(userAddressRepository.save(Mockito.any())).thenReturn(userAddress);

        User user = new User(1, "human");
        when(userRepository.findById(userAddress.getUserId())).thenReturn(Optional.of(user));

        UserAddressResponse actual = testRestTemplate.postForObject("/users/address", userAddress, UserAddressResponse.class);

        assertEquals("human@email.com", actual.getEmail());
        assertEquals("Human address", actual.getName());
        assertEquals("12/34 example road", actual.getAddress());
        assertEquals("10110", actual.getPostalCode());
        assertEquals("Chatuchak", actual.getDistrict());
        assertEquals("Krung Thep Maha Nakhon", actual.getProvince());
        assertEquals("0812345678", actual.getTelephone());
        assertEquals("human", actual.getUser().getUsername());
    }
}