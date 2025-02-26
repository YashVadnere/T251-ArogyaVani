package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier,Long>
{
    Optional<Supplier> findByEmail(String email);
}
