package com.humanresource.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.humanresource.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
