package com.learn.java.samplemicroservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@GetMapping(value = "/{name}")
	public String sayHello(@PathVariable String name) {
		
		return "Hello "+name+"! Have fun. Its Weekend";
	}
	
}
