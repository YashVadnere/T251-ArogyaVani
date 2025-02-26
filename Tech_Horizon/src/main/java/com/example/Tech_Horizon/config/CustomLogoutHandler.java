package com.example.Tech_Horizon.config;

import com.example.Tech_Horizon.entity.DonorToken;
import com.example.Tech_Horizon.entity.InstituteToken;
import com.example.Tech_Horizon.entity.SupplierToken;
import com.example.Tech_Horizon.repository.DonorTokenRepository;
import com.example.Tech_Horizon.repository.InstituteTokenRepository;
import com.example.Tech_Horizon.repository.SupplierTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomLogoutHandler implements LogoutHandler
{
    private final DonorTokenRepository donorTokenRepository;
    private final SupplierTokenRepository supplierTokenRepository;
    private final InstituteTokenRepository instituteTokenRepository;

    @Autowired
    public CustomLogoutHandler(
            DonorTokenRepository donorTokenRepository,
            SupplierTokenRepository supplierTokenRepository,
            InstituteTokenRepository instituteTokenRepository
    ) {
        this.donorTokenRepository = donorTokenRepository;
        this.supplierTokenRepository = supplierTokenRepository;
        this.instituteTokenRepository = instituteTokenRepository;
    }

    @Override
    public void logout(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Authentication authentication
    ) {

        String authHeader=request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return;
        }

        String jwtToken=authHeader.substring(7);
        Optional<DonorToken> optionalDonorToken=donorTokenRepository.findByToken(jwtToken);
        Optional<SupplierToken> optionalSupplierToken=supplierTokenRepository.findByToken(jwtToken);
        Optional<InstituteToken> optionalInstituteToken=instituteTokenRepository.findByToken(jwtToken);
        if(optionalDonorToken.isPresent())
        {
            DonorToken donorToken=optionalDonorToken.get();
            donorToken.setLoggedOut(true);
            donorTokenRepository.save(donorToken);
        }
        else if(optionalSupplierToken.isPresent())
        {
            SupplierToken supplierToken=optionalSupplierToken.get();
            supplierToken.setLoggedOut(true);
            supplierTokenRepository.save(supplierToken);
        }
        else if(optionalInstituteToken.isPresent())
        {
            InstituteToken instituteToken=optionalInstituteToken.get();
            instituteToken.setLoggedOut(true);
            instituteTokenRepository.save(instituteToken);
        }

    }
}
