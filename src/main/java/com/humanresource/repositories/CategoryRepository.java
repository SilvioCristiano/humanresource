package com.humanresource.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.humanresource.domain.Category;

	@Repository
	public interface CategoryRepository extends JpaRepository<Category, Integer> {

	}
