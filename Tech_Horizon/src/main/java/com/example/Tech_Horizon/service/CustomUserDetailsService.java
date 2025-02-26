package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.entity.Donor;
import com.example.Tech_Horizon.repository.DonorRepository;
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

    @Autowired
    public CustomUserDetailsService(
            DonorRepository donorRepository
    ) {
        this.donorRepository = donorRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<Donor> optionalDonor=donorRepository.findByEmail(username);
        if(optionalDonor.isPresent())
        {
            return optionalDonor.get();
        }
        else
        {
            throw new UsernameNotFoundException("User not found by username "+username);
        }
    }
}
