package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.entity.Donor;
import com.example.Tech_Horizon.entity.Institute;
import com.example.Tech_Horizon.entity.Supplier;
import com.example.Tech_Horizon.repository.DonorRepository;
import com.example.Tech_Horizon.repository.InstituteRepository;
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
    private final InstituteRepository instituteRepository;

    @Autowired
    public CustomUserDetailsService(
            DonorRepository donorRepository,
            SupplierRepository supplierRepository,
            InstituteRepository instituteRepository
    ) {
        this.donorRepository = donorRepository;
        this.supplierRepository = supplierRepository;
        this.instituteRepository = instituteRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<Donor> optionalDonor=donorRepository.findByEmail(username);
        Optional<Supplier> optionalSupplier=supplierRepository.findByEmail(username);
        Optional<Institute> optionalInstitute=instituteRepository.findByEmail(username);
        if(optionalDonor.isPresent())
        {
            return optionalDonor.get();
        }
        else if(optionalSupplier.isPresent())
        {
            return optionalSupplier.get();
        }
        else if(optionalInstitute.isPresent())
        {
            return optionalInstitute.get();
        }
        else
        {
            throw new UsernameNotFoundException("User not found by username "+username);
        }
    }
}
