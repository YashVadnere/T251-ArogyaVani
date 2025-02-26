package com.example.Tech_Horizon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "donor_tokens")
public class DonorToken extends Token
{
    @ManyToOne
    @JoinColumn(
            name = "donor_id",
            referencedColumnName = "donorId"
    )
    @JsonBackReference
    private Donor donor;
}
