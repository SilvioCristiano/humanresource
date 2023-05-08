package com.humanresource.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.humanresource.domain.City;
import com.humanresource.domain.State;
import com.humanresource.dto.CityDTO;
import com.humanresource.dto.StateDTO;
import com.humanresource.service.CityService;
import com.humanresource.service.StateService;

@RestController
@RequestMapping(value="/estados")
public class StateResource {
	
	@Autowired
	private StateService service;
	
	@Autowired
	private CityService cityService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<StateDTO>> findAll() {
		List<State> list = service.findAll();
		List<StateDTO> listDto = list.stream().map(obj -> new StateDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{stateId}/cities", method=RequestMethod.GET)
	public ResponseEntity<List<CityDTO>> findCities(@PathVariable Integer stateId) {
		List<City> list = cityService.findByEstado(stateId);
		List<CityDTO> listDto = list.stream().map(obj -> new CityDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
}
