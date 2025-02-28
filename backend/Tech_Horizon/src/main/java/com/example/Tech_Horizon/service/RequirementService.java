package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.dto.request.RequirementAddRequestDto;
import com.example.Tech_Horizon.dto.request.RequirementUpdateRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.entity.Institute;
import com.example.Tech_Horizon.entity.Requirement;
import com.example.Tech_Horizon.exception.ResourceNotFound;
import com.example.Tech_Horizon.repository.InstituteRepository;
import com.example.Tech_Horizon.repository.RequirementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RequirementService
{
    private final RequirementRepository requirementRepository;
    private final InstituteRepository instituteRepository;

    @Autowired
    public RequirementService(
            RequirementRepository requirementRepository,
            InstituteRepository instituteRepository
    ) {
        this.requirementRepository = requirementRepository;
        this.instituteRepository = instituteRepository;
    }


    @Transactional
    public ResponseDto addRequirement(Long id,RequirementAddRequestDto dto)
    {
        Optional<Institute> optionalInstitute=instituteRepository.findById(id);
        if(optionalInstitute.isPresent())
        {
            Requirement requirement=requirementAddRequestDtoToRequirement(dto);
            Institute institute=optionalInstitute.get();
            institute.getRequirements().add(requirement);
            requirement.setInstitute(institute);

            requirementRepository.save(requirement);
            instituteRepository.save(institute);

            ResponseDto responseDto=new ResponseDto();
            responseDto.setMessage("Requirement added");
            return responseDto;

        }
        throw new ResourceNotFound("User Not Found");
    }

    private Requirement requirementAddRequestDtoToRequirement(RequirementAddRequestDto dto)
    {
        Requirement requirement=new Requirement();
        requirement.setRequirement(dto.getRequirement());
        requirement.setQuantity(dto.getQuantity());
        requirement.setReceivedQuantity(0D);
        requirement.setUrgencyLevel(dto.getUrgencyLevel());
        requirement.setStatus("PENDING");
        requirement.setComment(dto.getComment());
        requirement.setRaisedAt(LocalDateTime.now());
        requirement.setFulfilledAt(null);
        return requirement;

    }

    public ResponseDto updateRequirement(Long requirementId, Long instituteId, RequirementUpdateRequestDto dto)
    {
        Optional<Institute> optionalInstitute=instituteRepository.findById(instituteId);
        Optional<Requirement> optionalRequirement=requirementRepository.findById(requirementId);


        if(optionalInstitute.isPresent() && optionalRequirement.isPresent())
        {
            if(Objects.equals(optionalRequirement.get().getInstitute().getInstituteId(), instituteId))
            {
                Institute institute=optionalInstitute.get();
                Requirement requirement=optionalRequirement.get();
                requirement.setQuantity(dto.getQuantity());
                requirement.setUrgencyLevel(dto.getUrgencyLevel());
                requirement.setComment(dto.getComment());
                requirementRepository.save(requirement);
                ResponseDto responseDto=new ResponseDto();
                responseDto.setMessage("Requirement updated successfully");
                return responseDto;
            }
            throw new IllegalArgumentException("Incorrect institute id and requirement id");
        }
        throw new ResourceNotFound("User not found");

    }


    public ResponseDto deleteRequirement(Long requirementId, Long instituteId)
    {
        Optional<Requirement> optionalRequirement=requirementRepository.findById(requirementId);
        Optional<Institute> optionalInstitute=instituteRepository.findById(instituteId);

        if(optionalInstitute.isPresent() && optionalRequirement.isPresent())
        {
            if(Objects.equals(optionalRequirement.get().getInstitute().getInstituteId(), instituteId))
            {
                Institute institute=optionalInstitute.get();
                Requirement requirement=optionalRequirement.get();
                institute.getRequirements().remove(requirement);
                instituteRepository.save(institute);
                requirementRepository.deleteById(requirementId);
                ResponseDto responseDto=new ResponseDto();
                responseDto.setMessage("Requirement deleted successfully");
                return responseDto;
            }
            throw new IllegalArgumentException("Incorrect institute id and requirement id");
        }
        throw new ResourceNotFound("User not found");
    }

    public List<Requirement> fetchAllRequirement(Long instituteId)
    {
        Optional<Institute> optionalInstitute=instituteRepository.findById(instituteId);

        if(optionalInstitute.isPresent())
        {
            return requirementRepository.findAllByInstitute_InstituteId(instituteId);
        }
        throw new ResourceNotFound("User not found");
    }

    public Requirement fetchRequirement(Long requirementId, Long instituteId)
    {
        Optional<Requirement> optionalRequirement=requirementRepository.findById(requirementId);
        Optional<Institute> optionalInstitute=instituteRepository.findById(instituteId);

        if(optionalInstitute.isPresent() && optionalRequirement.isPresent())
        {
            if(Objects.equals(optionalRequirement.get().getInstitute().getInstituteId(), instituteId))
            {
                return requirementRepository.findByInstitute_InstituteIdAndRequirementId(instituteId,requirementId);
            }
            throw new IllegalArgumentException("Incorrect institute id and requirement id");
        }
        throw new ResourceNotFound("User not found");

    }
}
