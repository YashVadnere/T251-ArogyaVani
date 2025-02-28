package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.InstituteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteProductRepository extends JpaRepository<InstituteProduct,Long>
{

}
