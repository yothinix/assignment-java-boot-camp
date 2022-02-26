package com.yothinix.ecommerce.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAddress {
    @Id
    private Integer id;
    private Integer userId;
    private String email;
    private String name;
    private String address;
    private String postalCode;
    private String district;
    private String province;
    private String telephone;
}
