package com.example.Tech_Horizon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "institute_product")
public class InstituteProduct
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "institute_product_id"
    )
    @SequenceGenerator(
            name = "institute_product_id",
            sequenceName = "institute_product_id",
            allocationSize = 1
    )
    private Long instituteProductId;
    private String productName;
    private String category;
    private Long stockAvailable;

    @ManyToOne
    @JoinColumn(
            name = "institute_id",
            referencedColumnName = "instituteId"
    )
    @JsonBackReference
    private Institute institute;

}
