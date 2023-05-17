package com.humanresource.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.humanresource.domain.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

}
