package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation,Long>
{

}
