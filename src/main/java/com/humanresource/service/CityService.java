package com.humanresource.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanresource.domain.City;
import com.humanresource.repositories.CityRepository;

@Service
public class CityService {
	
	@Autowired
	private CityRepository repo;

	public List<City> findByEstado(Integer stateId) {
		return repo.findCidades(stateId);
	}
}