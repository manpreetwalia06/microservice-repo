package com.rest.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private MockMvc mvc;
	
	@Sql(scripts = "classpath:\\data_test.sql")
	@Test()
	@Order(1)
	public void testGetUsers() throws Exception {
		System.out.println("Get User Called");
		mvc.perform(get("/user/1")
	    		.with(httpBasic("admin", "admin"))
	    	    .contentType(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk())
	    	    .andExpect(jsonPath("$.firstName", is("Scott")));
	}
	
	@Test()
	@Order(2)
	void saveUser() throws Exception {
		System.out.println("Save User Called");
		String userJson = "{\n"
	 			+ "  \"id\": 2,\n"
	 			+ "  \"title\": \"string\",\n"
	 			+ "  \"firstName\": \"string\",\n"
	 			+ "  \"lastName\": \"string\",\n"
	 			+ "  \"mobileNumber\": \"string\",\n"
	 			+ "  \"address\": {\n"
	 			+ "    \"id\": 2,\n"
	 			+ "    \"postcode\": \"string\",\n"
	 			+ "    \"suburb\": \"string\",\n"
	 			+ "    \"state\": \"string\",\n"
	 			+ "    \"fullAddress\": \"string\"\n"
	 			+ "  }\n"
	 			+ "}";
	    mvc.perform(post("/user/")
	    		.with(httpBasic("admin", "admin"))
	    		.content(userJson)
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.accept(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk())
	    	    .andExpect(jsonPath("$.firstName", is("string")))
	    	    .andExpect(jsonPath("$.id", is(2)));
	}
	
	@Test
	@Order(3)
	void updateUser() throws Exception {
		String userJson = "{\n"
	 			+ "  \"id\": 2,\n"
	 			+ "  \"title\": \"string\",\n"
	 			+ "  \"firstName\": \"Harris\",\n"
	 			+ "  \"lastName\": \"string\",\n"
	 			+ "  \"mobileNumber\": \"string\",\n"
	 			+ "  \"address\": {\n"
	 			+ "    \"id\": 2,\n"
	 			+ "    \"postcode\": \"string\",\n"
	 			+ "    \"suburb\": \"string\",\n"
	 			+ "    \"state\": \"string\",\n"
	 			+ "    \"fullAddress\": \"string\"\n"
	 			+ "  }\n"
	 			+ "}";
	    mvc.perform(put("/user/2")
	    		.with(httpBasic("admin", "admin"))
	    		.content(userJson)
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.accept(MediaType.APPLICATION_JSON))
	    	    .andExpect(status().isOk())
	    	    .andExpect(jsonPath("$.firstName", is("Harris")));
	}

}
