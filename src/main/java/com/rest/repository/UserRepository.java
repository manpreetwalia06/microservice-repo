package com.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
