package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.dto.request.SupplierProductRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.entity.Supplier;
import com.example.Tech_Horizon.entity.SupplierProduct;
import com.example.Tech_Horizon.exception.ResourceNotFound;
import com.example.Tech_Horizon.repository.SupplierProductRepository;
import com.example.Tech_Horizon.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierProductService
{
    private final SupplierProductRepository supplierProductRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierProductService(
            SupplierProductRepository supplierProductRepository,
            SupplierRepository supplierRepository
    ) {
        this.supplierProductRepository = supplierProductRepository;
        this.supplierRepository = supplierRepository;
    }

    public ResponseDto addProduct(Long id,SupplierProductRequestDto dto)
    {
        Optional<Supplier> optionalSupplier=supplierRepository.findById(id);
        if(optionalSupplier.isPresent())
        {
            SupplierProduct supplierProduct=supplierProductDtoMapper(dto);
            Supplier supplier=optionalSupplier.get();
            supplierProduct.setSupplier(supplier);
            supplier.getSupplierProducts().add(supplierProduct);
            supplierProductRepository.save(supplierProduct);
            supplierRepository.save(supplier);
            ResponseDto responseDto=new ResponseDto();
            responseDto.setMessage("Product added successfully");
            return responseDto;
        }
        throw new ResourceNotFound("Supplier Not Found");
    }

    private SupplierProduct supplierProductDtoMapper(SupplierProductRequestDto dto)
    {
        SupplierProduct supplierProduct=new SupplierProduct();
        supplierProduct.setProductName(dto.getProductName());
        supplierProduct.setCategory(dto.getCategory());
        supplierProduct.setPrice(dto.getPrice());
        supplierProduct.setUnit(dto.getUnit());
        supplierProduct.setStockAvailable(dto.getStockAvailable());
        return supplierProduct;
    }

    public ResponseDto updateProduct(Long productId, Long supplierId, SupplierProductRequestDto dto)
    {
        Optional<SupplierProduct> optionalSupplierProduct=supplierProductRepository.findById(productId);
        Optional<Supplier> optionalSupplier=supplierRepository.findById(supplierId);

        if(optionalSupplier.isPresent() && optionalSupplierProduct.isPresent())
        {
            if(optionalSupplierProduct.get().getSupplier().getSupplierId().equals(supplierId))
            {
                SupplierProduct supplierProduct=optionalSupplierProduct.get();
                Supplier supplier=optionalSupplier.get();

                supplierProduct.setProductName(dto.getProductName());
                supplierProduct.setCategory(dto.getCategory());
                supplierProduct.setPrice(dto.getPrice());
                supplierProduct.setUnit(dto.getUnit());
                supplierProduct.setStockAvailable(dto.getStockAvailable());
                supplierProductRepository.save(supplierProduct);
                ResponseDto responseDto=new ResponseDto();
                responseDto.setMessage("Product updated successfully");
                return responseDto;
            }
            throw new IllegalArgumentException("The product does not belong to the given supplier");
        }
        throw new ResourceNotFound("Invalid product or supplier id");
    }

    public ResponseDto deleteProduct(Long supplierId, Long productId)
    {
        Optional<Supplier> optionalSupplier=supplierRepository.findById(supplierId);
        Optional<SupplierProduct> optionalSupplierProduct=supplierProductRepository.findById(productId);

        if(optionalSupplier.isPresent() && optionalSupplierProduct.isPresent())
        {
            if(optionalSupplierProduct.get().getSupplier().getSupplierId().equals(supplierId))
            {
                Supplier supplier=optionalSupplier.get();
                SupplierProduct supplierProduct=optionalSupplierProduct.get();
                supplier.getSupplierProducts().remove(supplierProduct);
                supplierRepository.save(supplier);
                supplierProductRepository.deleteById(productId);
                ResponseDto responseDto=new ResponseDto();
                responseDto.setMessage("Product deleted successfully");
                return responseDto;
            }
            throw new IllegalArgumentException("The product does not belong to the given supplier");
        }
        throw new ResourceNotFound("Invalid product or supplier id");

    }

    public List<SupplierProduct> fetchAllSupplierProduct(Long supplierId)
    {
        Optional<List<SupplierProduct>> optionalSupplierProduct=supplierProductRepository.findBySupplier_SupplierId(supplierId);
        if(optionalSupplierProduct.isPresent())
        {
            return optionalSupplierProduct.get();
        }
        throw new ResourceNotFound("Product not found");
    }

    public SupplierProduct fetchSupplierProduct(Long supplierId, Long productId)
    {
        Optional<Supplier> optionalSupplier=supplierRepository.findById(supplierId);
        Optional<SupplierProduct> optionalSupplierProduct=supplierProductRepository.findById(productId);
        if(optionalSupplier.isPresent() && optionalSupplierProduct.isPresent())
        {
            if(optionalSupplierProduct.get().getSupplier().getSupplierId().equals(supplierId))
            {
                return optionalSupplierProduct.get();
            }
        }
        throw new ResourceNotFound("Invalid product or supplier id");
    }

    public List<SupplierProduct> fetchAllProductsUsingAddress(String address)
    {
        Optional<List<SupplierProduct>> optionalSupplierProducts=supplierProductRepository
                .findBySupplier_AddressContaining(address);
        if(optionalSupplierProducts.isPresent())
        {
            return optionalSupplierProducts.get();
        }
        throw new ResourceNotFound("Product not found");
    }

    public List<SupplierProduct> fetchByCategory(String category)
    {
        Optional<List<SupplierProduct>> optionalSupplierProducts=supplierProductRepository.findByCategory(category);

        if(optionalSupplierProducts.isPresent())
        {
            return optionalSupplierProducts.get();
        }
        throw new ResourceNotFound("Product not found");

    }
}
