package com.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



import com.user.model.User;

public interface IUserRepo extends JpaRepository<User, Integer>{

	List<User> findUserByType(String type);
	
	List<User> findUserByPhone(String phone);
	List<User> findUserByName(String name);
	public String findUserByNameOnly(String name);
	
	
	
	
}
