package com.yothinix.ecommerce.users.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
