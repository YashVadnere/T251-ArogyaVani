package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct,Long>
{
    Optional<List<SupplierProduct>> findBySupplier_SupplierId(Long id);
    Optional<List<SupplierProduct>> findBySupplier_AddressContaining(String address);
    Optional<List<SupplierProduct>> findByCategory(String category);
}
