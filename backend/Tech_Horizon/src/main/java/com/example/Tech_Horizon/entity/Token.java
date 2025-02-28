package com.example.Tech_Horizon.entity;

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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tokens")
public class Token
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_id"
    )
    @SequenceGenerator(
            name = "token_id",
            sequenceName = "token_id",
            allocationSize = 1
    )
    private Long tokenId;
    private String token;
    private boolean isLoggedOut;

}
