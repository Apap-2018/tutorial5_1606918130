package com.apap.tutorial5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.repository.FlightDB;

@Service
@Transactional
public class FlightServiceImpl implements FlightService{
	@Autowired
	private FlightDB flightDB;
	
	@Override
	public void addFlight(FlightModel flight) {
		flightDB.save(flight);
	}

	@Override
	public void deleteFlightById(Long id) {
		flightDB.deleteById(id);
	}
	
	@Override
	public FlightModel getFlightDetailById (Long id){
		return flightDB.getOne(id);
	}

	@Override
	public List<FlightModel> getFlightList() {
		return flightDB.findAll();
	}

	@Override
	public FlightModel getFlightDetailByFlightNumber(String flightNumber) {
		FlightModel flight = flightDB.findByFlightNumber(flightNumber);
		return flight;
	}
	
}