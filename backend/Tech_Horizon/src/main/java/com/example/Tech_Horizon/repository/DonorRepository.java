package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor,Long>
{
    Optional<Donor> findByEmail(String email);
}
