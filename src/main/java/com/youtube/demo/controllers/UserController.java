package com.youtube.demo.controllers;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.demo.model.User;
import com.youtube.demo.service.UserService;
import com.youtube.demo.util.RestResponse;

//Interface para el Frontend

//Crear metodo RESt

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	protected UserService userService;
	
	protected ObjectMapper mapper;
	//Mensaje de respuesta
	@RequestMapping(value="/saveOrUpdate", method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String userJson) 
			throws JsonParseException, JsonMappingException, IOException {
		this.mapper = new ObjectMapper();
		User user= this.mapper.readValue(userJson, User.class);
		
		if(!this.validate(user)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"Los campos obligatorios no están diligenciados");
		}
		this.userService.save(user);
		return new RestResponse(HttpStatus.OK.value(), "Operacion exitosa");
	}
	
	@GetMapping("/sayHello")
	public String sayHello(){
		return "Hello World";
	}
	
	private boolean validate(User user) {
		boolean isValid = true;
		
		if(StringUtils.trimToNull(user.getFirstName()) == null) {
			isValid = false;
		}
		if(user.getFirstSurname() ==null) {
			isValid = false;	
	}
		if(user.getAddress() ==null) {
			isValid = false;
		}
		return isValid;
	}
}

