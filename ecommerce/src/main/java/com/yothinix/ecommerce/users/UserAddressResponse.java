package com.yothinix.ecommerce.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yothinix.ecommerce.users.entity.User;
import com.yothinix.ecommerce.users.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(value = {"user_id"})
public class UserAddressResponse extends UserAddress {
    private User user;

    public UserAddressResponse(UserAddress userAddress) {
        this.setId(userAddress.getId());
        this.setUserId(userAddress.getUserId());
        this.setEmail(userAddress.getEmail());
        this.setName(userAddress.getName());
        this.setAddress(userAddress.getAddress());
        this.setPostalCode(userAddress.getPostalCode());
        this.setDistrict(userAddress.getDistrict());
        this.setProvince(userAddress.getProvince());
        this.setTelephone(userAddress.getTelephone());
    }
}
