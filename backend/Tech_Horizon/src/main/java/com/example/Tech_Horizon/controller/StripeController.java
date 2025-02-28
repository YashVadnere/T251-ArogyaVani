package com.example.Tech_Horizon.controller;

import com.example.Tech_Horizon.dto.request.ProductRequest;
import com.example.Tech_Horizon.dto.response.StripeResponse;
import com.example.Tech_Horizon.entity.*;
import com.example.Tech_Horizon.exception.ResourceNotFound;
import com.example.Tech_Horizon.repository.*;
import com.example.Tech_Horizon.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
public class StripeController
{
    private final StripeService stripeService;
    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;
    private final InstituteRepository instituteRepository;
    private final SupplierRepository supplierRepository;
    private final RequirementRepository requirementRepository;

    @Autowired
    public StripeController(
            StripeService stripeService,
            DonationRepository donationRepository,
            DonorRepository donorRepository,
            InstituteRepository instituteRepository,
            SupplierRepository supplierRepository,
            RequirementRepository requirementRepository
    ) {
        this.stripeService = stripeService;
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
        this.instituteRepository = instituteRepository;
        this.supplierRepository = supplierRepository;
        this.requirementRepository = requirementRepository;
    }

    @PostMapping("/donor/{donor-id}/institute/{institute-id}/supplier/{supplier-id}/requirement/{requirement-id}")
    public ResponseEntity<StripeResponse> checkoutProducts(
            @PathVariable("donor-id") Long donorId,
            @PathVariable("institute-id") Long instituteId,
            @PathVariable("supplier-id") Long supplierId,
            @PathVariable("requirement-id")Long requirementId,
            @RequestBody ProductRequest productRequest
    ) {
        productRequest.setAmount(productRequest.getAmount()*100);
        Donor donor=donorRepository.findById(donorId).orElseThrow(()->new ResourceNotFound("Donor not found"));
        Institute institute=instituteRepository.findById(instituteId).orElseThrow(()->new ResourceNotFound("Institute not found"));
        Supplier supplier=supplierRepository.findById(supplierId).orElseThrow(()->new ResourceNotFound("Supplier not found"));
        Requirement requirement=requirementRepository.findById(requirementId).orElseThrow(()->new ResourceNotFound("Requirement not found"));
        Donation donation=new Donation();
        donation.setQuantity(productRequest.getQuantity());
        donation.setAmount(productRequest.getAmount());
        donation.setDonor(donor);
        donation.setSupplier(supplier);
        donation.setRequirement(requirement);
        donation.setInstitute(institute);

        institute.getDonations().add(donation);
        donor.getDonations().add(donation);
        requirement.getDonations().add(donation);
        supplier.getDonations().add(donation);

        instituteRepository.save(institute);
        requirementRepository.save(requirement);
        donorRepository.save(donor);
        supplierRepository.save(supplier);

        donationRepository.save(donation);
        StripeResponse stripeResponse=stripeService.checkoutResponse(productRequest);
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }
}
