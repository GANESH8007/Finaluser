package com.user.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.user.model.User;

@Service
public class UserServiceImp implements IUserService{
	
	@Autowired
	IUserRepo iUserRepo;
	
	
	@Override
	public Integer saveUser(User user) {
		User saveUser = iUserRepo.save(user);
		return saveUser.getId();
	}


	@Override
	public List<User> getAllUser() {
		return iUserRepo.findAll();
	}


	@Override
	public List<User> getUserByType(String UserType) {
		return iUserRepo.findUserByType(UserType);
	}


	@Override
	public List<User> getUserByPhone(String Phone) {
		return iUserRepo.findUserByPhone(Phone);
	}

	
	@Override
	public User getUser(String phone) {
		List<User> list=iUserRepo.findUserByPhone(phone);	
		return list.stream().filter(user -> user.getPhone().equals(phone)).findAny().orElse(null);
	}


	@Override
	public User getUserbyName(String userName) {
		List<User> list=iUserRepo.findUserByName(userName);	
		return list.stream().filter(user -> user.getName().equals(userName)).findAny().orElse(null);
	}
	
	

}
