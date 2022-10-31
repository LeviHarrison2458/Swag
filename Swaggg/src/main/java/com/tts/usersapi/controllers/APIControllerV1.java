package com.tts.usersapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tts.usersapi.models.APIUser;
import com.tts.usersapi.repository.APIUserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="UserAPI Version 1", description="Handles manipulation of database users")
@RequestMapping(path = "/v1")
@RestController
public class APIControllerV1 { 

	@Autowired
	APIUserRepository repository;

	@ApiOperation(value = "Get all users", response = APIUser.class, responseContainer = "List", notes = "Gets information for all available users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved api users"),
			@ApiResponse(code = 401, message = "You are not authorized to view the Api users")
	})
	@GetMapping("/users")
	public List<APIUser> getUsers(@RequestParam(value = "state", required = false) String state) {
		if (state != null) {
			return (List<APIUser>) repository.findByState(state);
		}
		return (List<APIUser>) repository.findAll();
	}

	@ApiOperation(value = "Get by Id", response = APIUser.class, notes = "Gets information of a single user using their unique Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved api user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the Api users")
	})
	@GetMapping("/users/{id}")
	public Optional<APIUser> getUserById(@PathVariable(value = "id") Long id) {
		return repository.findById(id);
	}

	@ApiOperation(value = "Add User", notes = "Adds a single user given a JSON body")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created api user"),
			@ApiResponse(code = 401, message = "You are not authorized to create Api users")
	})
	@PostMapping("/users")
	public void createUser(@RequestBody APIUser user) {
		System.out.println(user);
		repository.save(user);
	}

	@ApiOperation(value = "Put User by Id", notes = "Goes to a user by an inputted ID and edits with an inputted JSON body")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated api user"),
			@ApiResponse(code = 401, message = "You are not authorized to update Api users")
	})
	@PutMapping("/users/{id}")
	public void createUser(@PathVariable(value = "id") Long id, @RequestBody APIUser user) {
		repository.save(user);
	}

	
	@ApiOperation(value = "Delete user by Id", notes = "Deletes user by an inputted Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully deleted api user"),
			@ApiResponse(code = 401, message = "You are not authorized to delete Api users")
	})
	@DeleteMapping("/users/{id}")
	public void createUser(@PathVariable(value = "id") Long id) {
		repository.deleteById(id);
	}
}
