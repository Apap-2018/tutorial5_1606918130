package com.apap.tutorial5.service;


import java.util.List;

import com.apap.tutorial5.model.FlightModel;


public interface FlightService {
	FlightModel getFlightDetailById (Long id);
	
	FlightModel getFlightDetailByFlightNumber (String flightNumber);
	
	void addFlight(FlightModel flight);
	
	void deleteFlightById(Long id);
	
	List<FlightModel> getFlightList();
}