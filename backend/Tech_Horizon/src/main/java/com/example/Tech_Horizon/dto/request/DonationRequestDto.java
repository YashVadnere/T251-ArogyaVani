package com.example.Tech_Horizon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Async;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequestDto
{
    private Double quantity;
    private Double amount;
}
