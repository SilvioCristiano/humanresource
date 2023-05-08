package com.humanresource.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanresource.domain.State;
import com.humanresource.repositories.StateRepository;

@Service
public class StateService {
	
	@Autowired
	private StateRepository repo;
	
	public List<State> findAll() {
		return repo.findAllByOrderByName();
	}
}

