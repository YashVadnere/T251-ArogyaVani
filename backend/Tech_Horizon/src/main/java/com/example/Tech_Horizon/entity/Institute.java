package com.example.Tech_Horizon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
@Table(name = "institutes")
public class Institute implements UserDetails
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "institute_id"
    )
    @SequenceGenerator(
            name = "institute_id",
            sequenceName = "institute_id",
            allocationSize = 1
    )
    private Long instituteId;
    @NotBlank(message = "Registration number is required")
    @Column(unique = true)
    private String registrationNumber;
    @NotBlank(message = "Institute name is required")
    private String instituteName;
    @NotBlank(message = "Person incharge is required")
    private String personIncharge;
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Category is required")
    private String category;

    @OneToMany(mappedBy = "institute")
    @JsonIgnore
    private List<InstituteToken> instituteTokens;

    @OneToMany(mappedBy = "institute")
    @JsonManagedReference
    private List<Requirement> requirements;

    @OneToMany(mappedBy = "institute")
    @JsonManagedReference
    private List<InstituteProduct> instituteProducts;

    @OneToMany(mappedBy = "institute")
    private List<Donation> donations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority("ROLE_INSTITUTE"));
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
