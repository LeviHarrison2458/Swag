package com.tts.usersapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

@Api(value = "UserAPI Version 2", description = "Handles manipulation of database users. Increased validation of Http Requests. Mandatory state filter")
@RequestMapping(path = "/v2")
@RestController
public class APIControllerV2 {

	@Autowired
	APIUserRepository repository;

	@ApiOperation(value = "Get all users", response = APIUser.class, responseContainer = "List", notes = "Gets information for all available users")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully retrieved api users"),
			@ApiResponse(code = 400, message = "No state included in response"),
		})
	@GetMapping("/users")
	public ResponseEntity<List<APIUser>> getUsers(@RequestParam(value = "state", required = true) String state) {
		if (state.equals(null)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>((List<APIUser>) repository.findByState(state), HttpStatus.OK);
	}

	@ApiOperation(value = "Get by Id", response = APIUser.class, notes = "Gets information of a single user using their unique Id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully retrieved api user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the Api users"),
			@ApiResponse(code = 404, message = "User not found")
		})
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<APIUser>> getUserById(@PathVariable(value = "id") Long id) {
		Optional<APIUser> user = repository.findById(id);
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Add User", notes = "Adds a single user given a JSON body")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully created api user"),
			@ApiResponse(code = 400, message = "Improper formatting of request"),
			@ApiResponse(code = 401, message = "You are not authorized to create Api users") 
		})
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody APIUser user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		repository.save(new APIUser(user.getId(), user.getFirstName(), user.getLastName(), user.getState()));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Put User by Id", notes = "Goes to a user by an inputted ID and edits with an inputted JSON body")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully updated api user"),
			@ApiResponse(code = 400, message = "Improper formatting of request"),
			@ApiResponse(code = 401, message = "You are not authorized to update Api users"),
			@ApiResponse(code = 404, message = "User not found")


		})
	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable(value = "id") Long id, @RequestBody APIUser user,
			BindingResult bindingResult) {
		Optional<APIUser> updatingUser = repository.findById(id);
		if (!updatingUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		repository.save(new APIUser(id, user.getFirstName(), user.getLastName(), user.getState()));

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Delete user by Id", notes = "Deletes user by an inputted Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted api user"),
			@ApiResponse(code = 401, message = "You are not authorized to delete Api users") })
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
		Optional<APIUser> user = repository.findById(id);
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
