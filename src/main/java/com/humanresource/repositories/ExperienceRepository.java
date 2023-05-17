package com.humanresource.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.humanresource.domain.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

}
