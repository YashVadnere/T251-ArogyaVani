package com.example.Tech_Horizon.controller;

import com.example.Tech_Horizon.dto.request.RequirementAddRequestDto;
import com.example.Tech_Horizon.dto.request.RequirementUpdateRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.entity.Requirement;
import com.example.Tech_Horizon.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requirement")
public class RequirementController
{
    private final RequirementService requirementService;

    @Autowired
    public RequirementController(RequirementService requirementService)
    {
        this.requirementService = requirementService;
    }

    @GetMapping("/institute/{institute-id}/fetch-all")
    public ResponseEntity<List<Requirement>> fetchAllRequirement(@PathVariable("institute-id") Long instituteId)
    {
        return new ResponseEntity<>(requirementService.fetchAllRequirement(instituteId),HttpStatus.OK);
    }

    @GetMapping("/{requirement-id}/institute/{institute-id}/fetch")
    public ResponseEntity<Requirement> fetchRequirement(@PathVariable("requirement-id") Long requirementId,@PathVariable("institute-id") Long instituteId)
    {
        return new ResponseEntity<>(requirementService.fetchRequirement(requirementId,instituteId),HttpStatus.OK);
    }

    @PostMapping("/institute/{institute-id}/add")
    public ResponseEntity<ResponseDto> addRequirement(@PathVariable("institute-id") Long id, @RequestBody RequirementAddRequestDto dto)
    {
        return new ResponseEntity<>(requirementService.addRequirement(id,dto), HttpStatus.OK);
    }

    @PutMapping("/{requirement-id}/institute/{institute-id}/update")
    public ResponseEntity<ResponseDto> updateRequirement(
            @PathVariable("requirement-id") Long requirementId,
            @PathVariable("institute-id") Long instituteId,
            @RequestBody RequirementUpdateRequestDto dto
    ) {
        return new ResponseEntity<>(requirementService.updateRequirement(requirementId,instituteId,dto),HttpStatus.OK);
    }

    @DeleteMapping("/{requirement-id}/institute/{institute-id}/delete")
    public ResponseEntity<ResponseDto> deleteRequirement(
            @PathVariable("requirement-id") Long requirementId,
            @PathVariable("institute-id") Long instituteId
    ) {
        return new ResponseEntity<>(requirementService.deleteRequirement(requirementId,instituteId),HttpStatus.OK);
    }

}
