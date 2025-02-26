package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.config.JwtService;
import com.example.Tech_Horizon.dto.request.DonorSignInRequestDto;
import com.example.Tech_Horizon.dto.request.DonorSignUpRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.entity.Donor;
import com.example.Tech_Horizon.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DonorAuthenticationService
{
    private final PasswordEncoder passwordEncoder;
    private final DonorRepository donorRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public DonorAuthenticationService(
            PasswordEncoder passwordEncoder,
            DonorRepository donorRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.donorRepository = donorRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseDto signUpDonor(DonorSignUpRequestDto dto)
    {
        Donor donor=donorAuthenticationDtoToDonorMapper(dto);
        donorRepository.save(donor);
        ResponseDto responseDto=new ResponseDto();
        responseDto.setMessage("Sign-up Successfull");
        return responseDto;
    }

    private Donor donorAuthenticationDtoToDonorMapper(DonorSignUpRequestDto dto)
    {
        Donor donor=new Donor();
        donor.setFullName(dto.getFullName());
        donor.setEmail(dto.getEmail());
        donor.setPassword(passwordEncoder.encode(dto.getPassword()));
        donor.setPhoneNumber(dto.getPhoneNumber());
        donor.setAddress(dto.getAddress());
        return donor;
    }

    public ResponseDto signInDonor(DonorSignInRequestDto dto)
    {
        Optional<Donor> optionalDonor=donorRepository.findByEmail(dto.getEmail());
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword())
        );

        if(optionalDonor.isPresent())
        {
            Donor donor=optionalDonor.get();
            if(authentication.isAuthenticated())
            {
                ResponseDto responseDto=new ResponseDto();
                responseDto.setMessage(jwtService.generateToken(donor));
                return responseDto;
            }
            throw new BadCredentialsException("Incorrect username or password. Try Again!!!");
        }
        throw new UsernameNotFoundException("User not found by email "+dto.getEmail());
    }
}
