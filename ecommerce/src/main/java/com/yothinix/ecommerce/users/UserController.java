package com.yothinix.ecommerce.users;

import com.yothinix.ecommerce.users.entity.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users/address")
    public UserAddressResponse createUserAddress(@RequestBody UserAddress userAddress) {
        return userService.createAddress(userAddress);
    }
}
