package com.example.Tech_Horizon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequirementAddRequestDto
{
    private String requirement;
    private Double quantity;
    private String urgencyLevel;
    private String comment;
}
