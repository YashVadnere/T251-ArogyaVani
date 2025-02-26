package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.entity.Donor;
import com.example.Tech_Horizon.entity.Supplier;
import com.example.Tech_Horizon.repository.DonorRepository;
import com.example.Tech_Horizon.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final DonorRepository donorRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public CustomUserDetailsService(
            DonorRepository donorRepository,
            SupplierRepository supplierRepository
    ) {
        this.donorRepository = donorRepository;
        this.supplierRepository = supplierRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<Donor> optionalDonor=donorRepository.findByEmail(username);
        Optional<Supplier> optionalSupplier=supplierRepository.findByEmail(username);
        if(optionalDonor.isPresent())
        {
            return optionalDonor.get();
        }
        else if(optionalSupplier.isPresent())
        {
            return optionalSupplier.get();
        }
        else
        {
            throw new UsernameNotFoundException("User not found by username "+username);
        }
    }
}
