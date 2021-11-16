package com.rest.controllers;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.exceptions.UserNotFoundException;
import com.rest.model.User;
import com.rest.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	//@Autowired
	//private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotFoundException {
		Optional<User> user = userService.getUser(id);
		//Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			throw new UserNotFoundException("User not found for id: "+id );
		}
	}
	
	@PostMapping(path = "/")
	public ResponseEntity<User> saveUser(@RequestBody User user) throws Exception {
		if(user.getId() !=null) {
			Optional<User> existingUser = userService.getUser(user.getId());
			//Optional<User> existingUser = userRepository.findById(user.getId());
			if(existingUser.isPresent()) {
				throw new Exception("User already exits with ID "+ user.getId());
			} else {
				User newUser = userService.saveUser(user);
				//User newUser = userRepository.save(user);
			    return new ResponseEntity<>(newUser, HttpStatus.OK);
			}
		} else {
			User newUser = userService.saveUser(user);
			//User newUser = userRepository.save(user);
		    return new ResponseEntity<>(newUser, HttpStatus.OK);
		}
		
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User user) throws Exception {
		//Optional<User> existingUser = userRepository.findById(user.getId());
		Optional<User> existingUser = userService.getUser(user.getId());
		if(existingUser.isPresent()) {
			User _user = existingUser.get();
			BeanUtils.copyProperties(user, _user, "id");
			//_user = userRepository.save(user);
			_user = userService.saveUser(user);
		    return new ResponseEntity<>(_user, HttpStatus.OK);		
		} else {
			throw new UserNotFoundException("User not found for id: "+id );	
		}
	}
}
