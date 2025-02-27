package com.example.Tech_Horizon.controller;

import com.example.Tech_Horizon.dto.request.SupplierProductRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.entity.SupplierProduct;
import com.example.Tech_Horizon.service.SupplierProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierProductController
{
    private final SupplierProductService supplierProductService;

    @Autowired
    public SupplierProductController(SupplierProductService supplierProductService)
    {
        this.supplierProductService = supplierProductService;
    }

    @GetMapping("/address")
    public ResponseEntity<List<SupplierProduct>> fetchAllProductsByAddress(@RequestParam String address)
    {
        return new ResponseEntity<>(supplierProductService.fetchAllProductsUsingAddress(address),HttpStatus.OK);
    }

    @GetMapping("/{supplier-id}/product/{product-id}")
    public ResponseEntity<SupplierProduct> fetchSupplierProduct(
            @PathVariable("supplier-id") Long supplierId,
            @PathVariable("product-id") Long productId
    ) {
        return new ResponseEntity<>(supplierProductService.fetchSupplierProduct(supplierId,productId),HttpStatus.OK);
    }

    @GetMapping("/product/category")
    public ResponseEntity<List<SupplierProduct>> fetchByCategory(@RequestParam String category)
    {
        return new ResponseEntity<>(supplierProductService.fetchByCategory(category),HttpStatus.OK);
    }

    @GetMapping("/{supplier-id}/fetch-all")
    public ResponseEntity<List<SupplierProduct>> fetchAllSupplierProduct(@PathVariable("supplier-id") Long supplierId)
    {
        return new ResponseEntity<>(supplierProductService.fetchAllSupplierProduct(supplierId),HttpStatus.OK);
    }

    @PostMapping("/{supplier-id}/product")
    public ResponseEntity<ResponseDto> addProduct(@PathVariable("supplier-id") Long supplierId, @RequestBody SupplierProductRequestDto dto)
    {
        return new ResponseEntity<>(supplierProductService.addProduct(supplierId,dto), HttpStatus.OK);
    }

    @PutMapping("/{supplier-id}/product/{product-id}/update")
    public ResponseEntity<ResponseDto> updateProduct(
            @PathVariable("product-id") Long productId,
            @PathVariable("supplier-id") Long supplierId,
            @RequestBody SupplierProductRequestDto dto
    ) {
        return new ResponseEntity<>(supplierProductService.updateProduct(productId,supplierId,dto),HttpStatus.OK);
    }

    @DeleteMapping("/{supplier-id}/product/{product-id}/delete")
    public ResponseEntity<ResponseDto> deleteProduct(
            @PathVariable("supplier-id") Long supplierId,
            @PathVariable("product-id") Long productId
    ) {
        return new ResponseEntity<>(supplierProductService.deleteProduct(supplierId,productId),HttpStatus.OK);
    }



}
