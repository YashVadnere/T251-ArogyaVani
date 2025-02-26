package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.config.JwtService;
import com.example.Tech_Horizon.dto.request.DonorSignInRequestDto;
import com.example.Tech_Horizon.dto.request.DonorSignUpRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.entity.Donor;
import com.example.Tech_Horizon.entity.DonorToken;
import com.example.Tech_Horizon.repository.DonorRepository;
import com.example.Tech_Horizon.repository.DonorTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorAuthenticationService
{
    private final PasswordEncoder passwordEncoder;
    private final DonorRepository donorRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final DonorTokenRepository donorTokenRepository;

    @Autowired
    public DonorAuthenticationService(
            PasswordEncoder passwordEncoder,
            DonorRepository donorRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            DonorTokenRepository donorTokenRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.donorRepository = donorRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.donorTokenRepository = donorTokenRepository;
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
                String jwtToken=jwtService.generateToken(donor);
                revokeAllTokens(donor);
                saveToken(jwtToken,donor);
                responseDto.setMessage(jwtToken);
                return responseDto;
            }
            throw new BadCredentialsException("Incorrect username or password. Try Again!!!");
        }
        throw new UsernameNotFoundException("User not found by email "+dto.getEmail());
    }

    private void saveToken(String jwtToken, Donor donor)
    {
        DonorToken donorToken=new DonorToken();
        donorToken.setToken(jwtToken);
        donorToken.setLoggedOut(false);
        donorToken.setDonor(donor);
        donorTokenRepository.save(donorToken);
    }

    private void revokeAllTokens(Donor donor)
    {
        List<DonorToken> donorTokenList=donorTokenRepository.findAllByDonor_DonorId(donor.getDonorId());
        if(!donorTokenList.isEmpty())
        {
            donorTokenList.forEach(token->token.setLoggedOut(true));
            donorTokenRepository.saveAll(donorTokenList);
        }

    }

}
