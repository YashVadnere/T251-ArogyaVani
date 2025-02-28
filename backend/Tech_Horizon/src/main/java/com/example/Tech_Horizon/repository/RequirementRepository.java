package com.example.Tech_Horizon.repository;

import com.example.Tech_Horizon.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement,Long>
{
    List<Requirement> findAllByInstitute_InstituteId(Long id);
    Requirement findByInstitute_InstituteIdAndRequirementId(Long instituteId,Long requirementId);
}
