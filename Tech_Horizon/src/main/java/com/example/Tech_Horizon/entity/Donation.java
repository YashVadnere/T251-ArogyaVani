package com.example.Tech_Horizon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "donations")
public class Donation
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "donation_id"
    )
    @SequenceGenerator(
            name = "donation_id",
            sequenceName = "donation_id",
            allocationSize = 1
    )
    private Long donationId;
    @NotNull(message = "Quantity is required")
    private Double quantity;
    @NotNull(message = "Amount is required")
    private Double amount;
    @NotBlank(message = "Status is required")
    private String status;
    private LocalDateTime createdAt;
}
