package com.example.Tech_Horizon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonorSignUpRequestDto
{
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
