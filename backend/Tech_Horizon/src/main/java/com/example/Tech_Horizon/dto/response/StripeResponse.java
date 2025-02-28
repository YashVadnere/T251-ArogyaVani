package com.example.Tech_Horizon.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeResponse
{
    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;
}
