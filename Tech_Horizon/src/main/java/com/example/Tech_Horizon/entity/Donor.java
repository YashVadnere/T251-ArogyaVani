package com.example.Tech_Horizon.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
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
@Table(name = "donors")
public class Donor implements UserDetails
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "donor_id"
    )
    @SequenceGenerator(
            name = "donor_id",
            sequenceName = "donor_id",
            allocationSize = 1
    )
    private Long donorId;
    @NotBlank(message = "Fullname is required")
    private String fullName;
    @Column(unique = true)
    @Email(message = "Invalid Email format")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;
    @NotBlank(message = "Address is required")
    private String address;

    @OneToMany(mappedBy = "donor")
    @JsonManagedReference
    private List<DonorToken> donorTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority("ROLE_DONOR"));
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
