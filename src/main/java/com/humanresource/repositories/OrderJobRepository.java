package com.humanresource.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.humanresource.domain.Customer;
import com.humanresource.domain.OrderJob;

@Repository
public interface OrderJobRepository extends JpaRepository<OrderJob, Integer> {

	@Transactional(readOnly=true)
	Page<OrderJob> findByCustomer(Customer customer, Pageable pageRequest);
}
