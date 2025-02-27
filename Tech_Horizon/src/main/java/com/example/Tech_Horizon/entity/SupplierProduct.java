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
@Table(name = "supplier_product")
public class SupplierProduct
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_id"
    )
    @SequenceGenerator(
            name = "product_id",
            sequenceName = "product_id",
            allocationSize = 1
    )
    private Long supplierProductId;
    private String productName;
    private String category;
    private Double price;
    private String unit;
    private Long stockAvailable;

    @ManyToOne
    @JoinColumn(
            name = "supplier_id",
            referencedColumnName = "supplierId"
    )
    @JsonBackReference
    private Supplier supplier;

}
