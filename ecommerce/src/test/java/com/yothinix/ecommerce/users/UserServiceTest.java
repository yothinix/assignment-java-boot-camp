package com.yothinix.ecommerce.users;

import com.yothinix.ecommerce.users.entity.User;
import com.yothinix.ecommerce.users.entity.UserAddress;
import com.yothinix.ecommerce.users.repository.UserAddressRepository;
import com.yothinix.ecommerce.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserAddressRepository userAddressRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void createAddressSuccessTest() {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(1);
        userAddress.setEmail("human@email.com");
        userAddress.setName("Human address");
        userAddress.setAddress("12/34 example road");
        userAddress.setPostalCode("10110");
        userAddress.setDistrict("Chatuchak");
        userAddress.setProvince("Krung Thep Maha Nakhon");
        userAddress.setTelephone("0812345678");
        when(userAddressRepository.save(userAddress)).thenReturn(userAddress);

        User user = new User(1, "human");
        when(userRepository.findById(userAddress.getUserId())).thenReturn(Optional.of(user));

        UserAddressResponse actual = userService.createAddress(userAddress);

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