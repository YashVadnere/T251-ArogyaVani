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
@Table(name = "institute_tokens")
public class InstituteToken extends Token
{
    @ManyToOne
    @JoinColumn(
            name = "institute_id",
            referencedColumnName = "instituteId"
    )
    private Institute institute;
}
