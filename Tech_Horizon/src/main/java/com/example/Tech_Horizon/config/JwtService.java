package com.example.Tech_Horizon.config;

import com.example.Tech_Horizon.entity.DonorToken;
import com.example.Tech_Horizon.entity.InstituteToken;
import com.example.Tech_Horizon.entity.SupplierToken;
import com.example.Tech_Horizon.repository.DonorTokenRepository;
import com.example.Tech_Horizon.repository.InstituteTokenRepository;
import com.example.Tech_Horizon.repository.SupplierTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService
{

    private final DonorTokenRepository donorTokenRepository;
    private final SupplierTokenRepository supplierTokenRepository;
    private final InstituteTokenRepository instituteTokenRepository;


    @Value("${spring.security.SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${spring.security.EXPIRATION}")
    private int EXPIRATION;

    @Autowired
    public JwtService(
            DonorTokenRepository donorTokenRepository,
            SupplierTokenRepository supplierTokenRepository,
            InstituteTokenRepository instituteTokenRepository
    ) {
        this.donorTokenRepository = donorTokenRepository;
        this.supplierTokenRepository = supplierTokenRepository;
        this.instituteTokenRepository = instituteTokenRepository;
    }

    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims=new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuer("ArogyaVani")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey()
    {
        byte[] decode= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String jwtToken)
    {
        return extractClaims(jwtToken, Claims::getSubject);
    }

    private <T>T extractClaims(String jwtToken, Function<Claims,T> claimsResolver)
    {
        Claims claims=extractClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String jwtToken)
    {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails)
    {
        String username=extractUsername(jwtToken);
        boolean isValid=false;
        Optional<DonorToken> optionalDonorToken=donorTokenRepository.findByToken(jwtToken);
        Optional<SupplierToken> optionalSupplierToken=supplierTokenRepository.findByToken(jwtToken);
        Optional<InstituteToken> optionalInstituteToken=instituteTokenRepository.findByToken(jwtToken);
        if(optionalDonorToken.isPresent())
        {
            DonorToken donorToken=optionalDonorToken.get();
            if(donorToken.isLoggedOut()==false)
            {
                isValid=true;
            }
        }
        else if(optionalSupplierToken.isPresent())
        {
            SupplierToken supplierToken=optionalSupplierToken.get();
            if(supplierToken.isLoggedOut()==false)
            {
                isValid=true;
            }
        }
        else if(optionalInstituteToken.isPresent())
        {
            InstituteToken instituteToken=optionalInstituteToken.get();
            if(instituteToken.isLoggedOut()==false)
            {
                isValid=true;
            }
        }
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken) && isValid);
    }

    private boolean isTokenExpired(String jwtToken)
    {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken)
    {
        return extractClaims(jwtToken,Claims::getExpiration);
    }
}
