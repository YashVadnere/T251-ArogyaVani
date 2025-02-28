package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituteRepository extends JpaRepository<Institute,Long>
{
    Optional<Institute> findByEmail(String email);
}
