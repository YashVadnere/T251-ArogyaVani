package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.SupplierToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierTokenRepository extends JpaRepository<SupplierToken,Long>
{
    List<SupplierToken> findAllBySupplier_SupplierId(Long id);
    Optional<SupplierToken> findByToken(String jwtToken);
}
