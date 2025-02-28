package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.InstituteToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstituteTokenRepository extends JpaRepository<InstituteToken,Long>
{
    List<InstituteToken> findByInstitute_InstituteId(Long id);
    Optional<InstituteToken> findByToken(String jwtToken);
}
