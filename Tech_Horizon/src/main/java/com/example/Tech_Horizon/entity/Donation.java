package com.example.Tech_Horizon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long quantity;
    @NotNull(message = "Amount is required")
    private Long amount;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(
            name = "donor_id",
            referencedColumnName = "donorId"
    )
    @JsonBackReference
    private Donor donor;

    @ManyToOne
    @JoinColumn(
            name = "supplier_id",
            referencedColumnName = "supplierId"
    )
    @JsonBackReference
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(
            name = "requirement_id",
            referencedColumnName = "requirementId"
    )
    @JsonBackReference
    private Requirement requirement;

    @ManyToOne
    @JoinColumn(
            name = "institute_id",
            referencedColumnName = "instituteId"
    )
    private Institute institute;
}
