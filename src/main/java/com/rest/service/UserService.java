package com.rest.service;

import java.sql.SQLException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.model.User;
import com.rest.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	@Transactional(rollbackOn = SQLException.class)
	public User saveUser(User user) throws Exception{
		return userRepository.save(user);
	}

}
