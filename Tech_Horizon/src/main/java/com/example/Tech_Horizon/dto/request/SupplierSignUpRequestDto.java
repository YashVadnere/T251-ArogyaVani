package com.example.Tech_Horizon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierSignUpRequestDto
{
    private String gstNumber;
    private String shopName;
    private String ownerName;
    private String email;
    private String password;
    private String address;
    private String category;
}
