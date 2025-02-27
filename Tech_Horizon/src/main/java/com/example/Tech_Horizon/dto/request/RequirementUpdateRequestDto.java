package com.example.Tech_Horizon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequirementUpdateRequestDto
{
    private Double quantity;
    private String urgencyLevel;
    private String comment;
}
