package com.example.Tech_Horizon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requirements")
public class Requirement
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "requirement_id"
    )
    @SequenceGenerator(
            name = "requirement_id",
            sequenceName = "requirement_id",
            allocationSize = 1
    )
    private Long requirementId;
    @NotBlank(message = "Requirement is required")
    private String requirement;
    @NotNull(message = "Quantity is required")
    private Double quantity;
    @NotNull(message = "Received quantity is required")
    private Double receivedQuantity;
    @NotBlank(message = "Urgency level is required")
    private String urgencyLevel;
    @NotBlank(message = "Status is required")
    private String status;
    @NotBlank(message = "Comment is required")
    private String comment;
    private LocalDateTime raisedAt;
    private LocalDateTime fulfilledAt;

    @ManyToOne
    @JoinColumn(
            name = "institute_id",
            referencedColumnName = "instituteId"
    )
    @JsonBackReference
    private Institute institute;

    @OneToMany(mappedBy = "requirement")
    @JsonManagedReference
    private List<Donation> donations;

}
