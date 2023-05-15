package com.radovan.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.InstanceNotExistException;
import com.radovan.spring.service.UserService;

@Service
public class UserDetailsImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserEntity loadUserByUsername(String name)  {
		UserEntity user = userService.getUserByEmail(name);
		if (user == null) {
			Error error = new Error("User not exist");
			throw new InstanceNotExistException(error);
		}

		return user;

	}
}
