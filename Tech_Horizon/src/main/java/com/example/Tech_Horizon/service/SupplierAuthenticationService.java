package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.config.JwtService;
import com.example.Tech_Horizon.dto.request.SignInRequestDto;
import com.example.Tech_Horizon.dto.request.SupplierSignUpRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.entity.Supplier;
import com.example.Tech_Horizon.entity.SupplierToken;
import com.example.Tech_Horizon.repository.SupplierRepository;
import com.example.Tech_Horizon.repository.SupplierTokenRepository;
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
public class SupplierAuthenticationService
{
    private final SupplierRepository supplierRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SupplierTokenRepository supplierTokenRepository;

    @Autowired
    public SupplierAuthenticationService(
            SupplierRepository supplierRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            SupplierTokenRepository supplierTokenRepository
    ) {
        this.supplierRepository = supplierRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.supplierTokenRepository = supplierTokenRepository;
    }

    public ResponseDto signUpSupplier(SupplierSignUpRequestDto dto)
    {
        if (supplierRepository.findByEmail(dto.getEmail()).isPresent())
        {
            throw new IllegalArgumentException("Email is already registered");
        }
        Supplier supplier=supplierSignUpDtoToSupplierMapper(dto);
        supplierRepository.save(supplier);
        ResponseDto responseDto=new ResponseDto();
        responseDto.setMessage("Sign-up Successfull");

        return responseDto;
    }

    private Supplier supplierSignUpDtoToSupplierMapper(SupplierSignUpRequestDto dto)
    {
        Supplier supplier=new Supplier();
        supplier.setRegistrationId(dto.getRegistrationId());
        supplier.setShopName(dto.getShopName());
        supplier.setOwnerName(dto.getOwnerName());
        supplier.setEmail(dto.getEmail());
        supplier.setPassword(passwordEncoder.encode(dto.getPassword()));
        supplier.setAddress(dto.getAddress());
        supplier.setCategory(dto.getCategory());
        return supplier;
    }

    public ResponseDto signInSupplier(SignInRequestDto dto)
    {
        Optional<Supplier> optionalSupplier=supplierRepository.findByEmail(dto.getEmail());
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword())
        );

        if(optionalSupplier.isPresent())
        {
            Supplier supplier=optionalSupplier.get();
            if(authentication.isAuthenticated())
            {
                ResponseDto responseDto=new ResponseDto();
                String jwtToken= jwtService.generateToken(supplier);
                revokeAllTokens(supplier);
                saveToken(jwtToken,supplier);
                responseDto.setMessage(jwtToken);
                return responseDto;
            }
            throw new BadCredentialsException("Incorrect username or password");
        }
        throw new UsernameNotFoundException("User not found");
    }

    private void saveToken(String jwtToken, Supplier supplier)
    {
        SupplierToken supplierToken=new SupplierToken();
        supplierToken.setToken(jwtToken);
        supplierToken.setLoggedOut(false);
        supplierToken.setSupplier(supplier);
        supplierTokenRepository.save(supplierToken);
    }

    @Transactional
    private void revokeAllTokens(Supplier supplier)
    {
        List<SupplierToken> supplierTokenList=supplierTokenRepository.findAllBySupplier_SupplierId(supplier.getSupplierId());
        if(!supplierTokenList.isEmpty())
        {
            supplierTokenList.forEach(token->token.setLoggedOut(true));
            supplierTokenRepository.saveAll(supplierTokenList);
        }
    }



}
