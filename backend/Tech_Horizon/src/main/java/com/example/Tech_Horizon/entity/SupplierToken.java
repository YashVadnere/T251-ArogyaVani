package com.example.Tech_Horizon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier_tokens")
public class SupplierToken extends Token
{
    @ManyToOne
    @JoinColumn(
            name = "supplier_id",
            referencedColumnName = "supplierId"
    )
    private Supplier supplier;
}
