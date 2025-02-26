package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.config.JwtService;
import com.example.Tech_Horizon.dto.request.InstituteSignUpRequestDto;
import com.example.Tech_Horizon.dto.request.SignInRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.entity.Institute;
import com.example.Tech_Horizon.entity.InstituteToken;
import com.example.Tech_Horizon.entity.Supplier;
import com.example.Tech_Horizon.entity.SupplierToken;
import com.example.Tech_Horizon.repository.InstituteRepository;
import com.example.Tech_Horizon.repository.InstituteTokenRepository;
import jakarta.transaction.Transactional;
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
public class InstituteAuthenticationService
{
    private final InstituteRepository instituteRepository;
    private final InstituteTokenRepository instituteTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public InstituteAuthenticationService(
            InstituteRepository instituteRepository,
            InstituteTokenRepository instituteTokenRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.instituteRepository = instituteRepository;
        this.instituteTokenRepository = instituteTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseDto signUpInstitute(InstituteSignUpRequestDto dto)
    {
        if (instituteRepository.findByEmail(dto.getEmail()).isPresent())
        {
            throw new IllegalArgumentException("Email is already registered");
        }
        Institute institute=instituteSignUpDtoToInstituteMapper(dto);
        instituteRepository.save(institute);
        ResponseDto responseDto=new ResponseDto();
        responseDto.setMessage("Sign-up Successfull");
        return responseDto;
    }


    private Institute instituteSignUpDtoToInstituteMapper(InstituteSignUpRequestDto dto)
    {
        Institute institute=new Institute();
        institute.setRegistrationNumber(dto.getRegistrationNumber());
        institute.setInstituteName(dto.getInstituteName());
        institute.setPersonIncharge(dto.getPersonIncharge());
        institute.setEmail(dto.getEmail());
        institute.setPassword(passwordEncoder.encode(dto.getPassword()));
        institute.setAddress(dto.getAddress());
        institute.setCategory(dto.getCategory());

        return institute;
    }

    public ResponseDto signInIntitute(SignInRequestDto dto)
    {
        Optional<Institute> optionalInstitute=instituteRepository.findByEmail(dto.getEmail());
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword())
        );

        if(optionalInstitute.isPresent())
        {
            Institute institute=optionalInstitute.get();
            if(authentication.isAuthenticated())
            {
                ResponseDto responseDto=new ResponseDto();
                String jwtToken= jwtService.generateToken(institute);
                revokeAllTokens(institute);
                saveToken(jwtToken,institute);
                responseDto.setMessage(jwtToken);
                return responseDto;
            }
            throw new BadCredentialsException("Incorrect username or password");
        }
        throw new UsernameNotFoundException("User not found");
    }

    private void saveToken(String jwtToken, Institute institute)
    {
        InstituteToken instituteToken=new InstituteToken();
        instituteToken.setToken(jwtToken);
        instituteToken.setLoggedOut(false);
        instituteToken.setInstitute(institute);
        instituteTokenRepository.save(instituteToken);
    }

    @Transactional
    private void revokeAllTokens(Institute institute)
    {
        List<InstituteToken> instituteTokenList=instituteTokenRepository.findByInstitute_InstituteId(institute.getInstituteId());
        if(!instituteTokenList.isEmpty())
        {
            instituteTokenList.forEach(token->token.setLoggedOut(true));
            instituteTokenRepository.saveAll(instituteTokenList);
        }
    }
}
