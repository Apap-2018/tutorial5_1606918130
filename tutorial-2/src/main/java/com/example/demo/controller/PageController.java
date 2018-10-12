package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
	@RequestMapping("/viral")
	public String index() {
		return "viral";
	}
	
	@RequestMapping("/challenge")
	public String challenge(@RequestParam(value = "name") String name, Model model) {
		model.addAttribute("name",name);
		return "challenge";
	}

	@RequestMapping(value = {"/challenge", "/challenge/{name}"})
	public String challengePath(@PathVariable Optional<String> name, Model model) {
		if (name.isPresent()) {
			model.addAttribute("name",name.get());
		} else {
			model.addAttribute("name","KB");
		}
		return "challenge";
	}
	
	@RequestMapping("/generator")
	public String generator(@RequestParam(value = "a", defaultValue = "0") String vA, @RequestParam(value = "b", defaultValue = "0") String vB, Model model) {
		int firstNum = Integer.parseInt(vA);
		int secondNum = Integer.parseInt(vB);
		if(firstNum == 0) {
			firstNum = 1;
		}
		if(secondNum == 0) {
			secondNum = 1;
		}
		String hmmBefore = "hm";
		for(int i = 1; i < firstNum; i++) {
			hmmBefore += "m";
		}
		String hmmAfter = "";
		for(int i = 0; i < secondNum; i++) {
			hmmAfter += hmmBefore + " ";
		}
		
		model.addAttribute("nilaiA", vA);
		model.addAttribute("nilaiB", vB);
		model.addAttribute("text", hmmAfter);
		return "viralgenerator";
	}
}


