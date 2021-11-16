package com.rest.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.rest.model.Address;
import com.rest.model.User;
import com.rest.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	private Optional<User>  mockOptionalUser;
	
	private User user;
	
	private String userJson;	
	
	public void initialize() {
		 this.user = new User();
		user.setFirstName("Steven");
		user.setId(1L);
		user.setLastName("Scott");
		user.setMobileNumber("0999999999");
		user.setTitle("Mr");
		
		Address address = new Address();
		address.setFullAddress("Test Address");
		address.setId(1L);
		address.setPostcode("2150");
		address.setState("NSW");
		address.setSuburb("Parramatta");
		address.setUser(user);
		user.setAddress(address);
		mockOptionalUser =  Optional.of(user);
	 	this.userJson = "{\n"
	 			+ "  \"id\": 1,\n"
	 			+ "  \"title\": \"string\",\n"
	 			+ "  \"firstName\": \"string\",\n"
	 			+ "  \"lastName\": \"string\",\n"
	 			+ "  \"mobileNumber\": \"string\",\n"
	 			+ "  \"address\": {\n"
	 			+ "    \"id\": 1,\n"
	 			+ "    \"postcode\": \"string\",\n"
	 			+ "    \"suburb\": \"string\",\n"
	 			+ "    \"state\": \"string\",\n"
	 			+ "    \"fullAddress\": \"string\"\n"
	 			+ "  }\n"
	 			+ "}";
	}
	
	@Test
	void getUser() throws Exception {
		initialize();
	    when(userService.getUser(Mockito.anyLong())).thenReturn(mockOptionalUser);
	    mockMvc.perform(get("/user/1")
	    		.with(httpBasic("admin", "admin"))
	    	    .contentType(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk())
	    	    .andExpect(jsonPath("$.firstName", is(user.getFirstName())));
	}
	
	@Test
	void saveUser() throws Exception {
		initialize();
	    when(userService.getUser(Mockito.anyLong())).thenReturn(Optional.empty());
	    when(userService.saveUser(Mockito.any())).thenReturn(user);

	    mockMvc.perform(post("/user/")
	    		.with(httpBasic("admin", "admin"))
	    		.content(userJson)
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.accept(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk())
	    	    .andExpect(jsonPath("$.firstName", is(user.getFirstName())));
	}
	
	@Test
	void updateUser() throws Exception {
		initialize();
	    when(userService.getUser(Mockito.anyLong())).thenReturn(mockOptionalUser);
	    when(userService.saveUser(Mockito.any())).thenReturn(user);

	    mockMvc.perform(put("/user/1")
	    		.with(httpBasic("admin", "admin"))
	    		.content(userJson)
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.accept(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk())
	    	    .andExpect(jsonPath("$.firstName", is(user.getFirstName())));
	}
	

}
