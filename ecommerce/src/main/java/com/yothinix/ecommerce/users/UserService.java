package com.yothinix.ecommerce.users;

import com.yothinix.ecommerce.users.entity.User;
import com.yothinix.ecommerce.users.entity.UserAddress;
import com.yothinix.ecommerce.users.repository.UserAddressRepository;
import com.yothinix.ecommerce.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    UserRepository userRepository;

    public UserAddressResponse createAddress(UserAddress userAddress) {
        UserAddress newUserAddress = userAddressRepository.save(userAddress);

        UserAddressResponse response = new UserAddressResponse(newUserAddress);

        if (response.getUserId() != null) {
            Optional<User> optionalUser = userRepository.findById(response.getUserId());
            optionalUser.ifPresent(response::setUser);
        }

        return response;
    }
}
