package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.user.model.JwtResponse;
import com.user.model.User;
import com.user.service.IUserService;
import com.user.service.UserService;
import com.user.utility.JwUtility;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

	@Autowired
	private JwUtility jwUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
////
	@Autowired
	IUserService iUserService;
	
	@Autowired
	private RestTemplate restTemplate;
		
	
	@GetMapping("/")
	public String home()
	{
		return "Welcome to JWD project";
	}

	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody User jwtRequest) throws Exception
	{
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtRequest.getName(), jwtRequest.getPassword()));
	
		}catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Invalid Cred!!!",e); 
		}
		
		final UserDetails userDetails= userService.loadUserByUsername(jwtRequest.getName());
		final String token= jwUtility.generateToken(userDetails);
		
		return new JwtResponse(token);
	}
	
	//
	@PostMapping("/saveUser")
	Integer createEmployee(@RequestBody User user)
	{
		Integer id =iUserService.saveUser(user);
		return id;
	}
	
	@GetMapping("/getAllUser")
	List<User> getAllUser()
	{
		return iUserService.getAllUser();
	}
	
	
	@GetMapping("/getUserbyType/{Type}")
	List<User> getUserByType(@PathVariable String Type )
	{
		List<User> record=iUserService.getUserByType(Type);
		return record;
		
	}
	

	
	@GetMapping("getBooksDetailsByAutherName/{authName}")
	public User getBooksbyAuthorName(@PathVariable("authName") String authName) {
		User user=this.iUserService.getUserbyName(authName);
		
		List Records=this.restTemplate.getForObject("http:Book-Microservice/getBookByAuthor/"+authName, List.class);
		user.setRecords(Records);
		return user;	
	}
	


}
