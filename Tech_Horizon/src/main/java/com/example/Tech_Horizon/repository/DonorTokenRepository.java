package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.DonorToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorTokenRepository extends JpaRepository<DonorToken,Long>
{
    List<DonorToken> findAllByDonor_DonorId(Long id);
    Optional<DonorToken> findByToken(String jwtToken);
}
