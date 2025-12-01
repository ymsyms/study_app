package com.study.app_services.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users")
class UserController{
	
	//http://localhost:8080/users/1
	@GetMapping("/{userId}")
	public ResponseEntity<String> getUserDetails(@PathVariable Long userId){
		String userDetails = "User details for user ID " + userId;
		return ResponseEntity.ok(userDetails);
		
	}
	
	//http://localhost:8080/users/search?name=J
	@GetMapping("/search")
	public ResponseEntity<List<String>> searchUsers(@RequestParam("name") String name){
		List<String> users = Arrays.asList("John","Jane","Robert");
		return ResponseEntity.ok(users);
	}
	
	//http://localhost:8080/users/search?name=J
	@GetMapping("/list")
	public ResponseEntity<List<String>> getUsers(){
		List<String> users = Arrays.asList("John","Jane","Robert");
		return ResponseEntity.ok(users);
	}
	
}
