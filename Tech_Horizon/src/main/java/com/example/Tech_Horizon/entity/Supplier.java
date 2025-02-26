package com.example.Tech_Horizon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "suppliers")
public class Supplier implements UserDetails
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator ="supplier_id"
    )
    @SequenceGenerator(
            name = "supplier_id",
            sequenceName = "supplier_id",
            allocationSize = 1
    )
    private Long supplierId;
    @NotBlank(message = "Shop registration number required")
    private String registrationNumber;
    @NotBlank(message = "Shop name required")
    private String shopName;
    @NotBlank(message = "Owner name required")
    private String ownerName;
    @NotBlank(message = "Email required")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password required")
    private String password;
    @NotBlank(message = "Address Required")
    private String address;
    @NotBlank(message = "Category required")
    private String category;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private List<SupplierToken> supplierTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority("ROLE_SUPPLIER"));
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String getUsername()
    {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
