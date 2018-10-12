package com.apap.tutorial5.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		ArrayList<FlightModel> pilotFlight = new ArrayList<>();
		pilotFlight.add(flight);
		pilot.setPilotFlight(pilotFlight);
		
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", pilot);
		model.addAttribute("title", "Add - New Flight");
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"addRow"})
	public String addRow(@ModelAttribute PilotModel pilot,BindingResult bindingResult, Model model) {
		if(pilot.getPilotFlight() == null) {
			pilot.setPilotFlight(new ArrayList<FlightModel>());
		}
		
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params= {"submit"})
	private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
		PilotModel pilotnya = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for (FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(pilotnya);
			flightService.addFlight(flight);
		}
	
		return "add";
	}
	
	@RequestMapping(value = "/flight/delete/{id}", method = RequestMethod.POST)
	private String del(@PathVariable(value = "id") Long id, Model model) {
		flightService.deleteFlightById(id);
		return "delete";
	}
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlights(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		return "delete";
	}
		
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "id") Long id, Model model) {
		FlightModel flight = flightService.getFlightDetailById(id);
		
		model.addAttribute("flight", flight);
		model.addAttribute("title", "Update - Flight");
		return "update-flight";
	}
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.POST)
	private String updateSubmit(@PathVariable(value = "id") Long id,
			@RequestParam(value = "flightNumber") String flightNumber,
			@RequestParam(value = "origin") String origin,
			@RequestParam(value = "destination") String destination,
			@RequestParam(value = "time") Date time,Model model) {
		FlightModel flight = flightService.getFlightDetailById(id);
		flight.setFlightNumber(flightNumber);
		flight.setOrigin(origin);
		flight.setDestination(destination);
		flight.setTime(time);
		flightService.addFlight(flight);
		
		model.addAttribute("flight", flight);
		return "update";
	}

	@RequestMapping("/flight/viewall")
	public String viewall(Model model) {
		List<FlightModel> archive = flightService.getFlightList();
		
		model.addAttribute("flightList", archive);
		model.addAttribute("title", "View - All Flight");
		return "viewall-flight";
	}
	
	@RequestMapping("/flight/view/{flightNumber}")
	public String view(@PathVariable String flightNumber, Model model) {
		FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber);
		
		model.addAttribute("flight", flight);
		model.addAttribute("title", "view - flight");
		return "view-flight";
	}
}