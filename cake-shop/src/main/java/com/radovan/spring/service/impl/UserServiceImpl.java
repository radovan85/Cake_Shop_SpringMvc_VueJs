package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.ExistingEmailException;
import com.radovan.spring.exceptions.InstanceNotExistException;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.repository.UserRepository;
import com.radovan.spring.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public UserDto updateUser(Integer id, UserDto user) {
		// TODO Auto-generated method stub
		Optional<UserEntity> tempUserOpt = userRepository.findById(id);
		UserDto returnValue = null;

		if (tempUserOpt.isPresent()) {
			UserEntity userEntity = tempConverter.userDtoToEntity(user);
			userEntity.setEnabled(tempUserOpt.get().getEnabled());
			userEntity.setRoles(tempUserOpt.get().getRoles());
			userEntity.setId(tempUserOpt.get().getId());
			UserEntity updatedUser = userRepository.saveAndFlush(userEntity);
			returnValue = tempConverter.userEntityToDto(updatedUser);
		} else {
			Error error = new Error("Instace not exist!");
			throw new InstanceNotExistException(error);
		}

		return returnValue;
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		Optional<UserEntity> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			userRepository.clearUserRoles(id);
			userRepository.deleteById(id);
			userRepository.flush();
		} else {
			Error error = new Error("Instace not exist!");
			throw new InstanceNotExistException(error);
		}
	}

	@Override
	public UserDto getUserById(Integer id) {
		// TODO Auto-generated method stub
		Optional<UserEntity> userOpt = userRepository.findById(id);
		UserDto returnValue = null;

		if (userOpt.isPresent()) {
			returnValue = tempConverter.userEntityToDto(userOpt.get());
		} else {
			Error error = new Error("Instace not exist!");
			throw new InstanceNotExistException(error);
		}

		return returnValue;
	}

	@Override
	public List<UserDto> listAllUsers() {
		// TODO Auto-generated method stub
		Optional<List<UserEntity>> allUsersOpt = Optional.ofNullable(userRepository.findAll());
		List<UserDto> returnValue = new ArrayList<UserDto>();

		if (!allUsersOpt.isEmpty()) {
			allUsersOpt.get().forEach((userEntity) -> {
				UserDto userDto = tempConverter.userEntityToDto(userEntity);
				returnValue.add(userDto);
			});
		}
		return returnValue;
	}

	@Override
	public UserEntity getUserByEmail(String email) {
		// TODO Auto-generated method stub

		Optional<UserEntity> userOpt = Optional.ofNullable(userRepository.findByEmail(email));
		UserEntity returnValue = null;

		if (userOpt.isPresent()) {
			returnValue = userOpt.get();
		} else {
			Error error = new Error("Instace not exist!");
			throw new InstanceNotExistException(error);
		}

		return returnValue;
	}

	@Override
	public UserDto storeUser(UserDto user) {
		// TODO Auto-generated method stub

		Optional<UserEntity> testUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
		if (testUser.isPresent()) {
			Error error = new Error("Email exists");
			throw new ExistingEmailException(error);
		}
		RoleEntity role = roleRepository.findByRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled((byte) 1);
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(role);
		UserEntity userEntity = tempConverter.userDtoToEntity(user);
		userEntity.setRoles(roles);
		userEntity.setEnabled((byte) 1);
		UserEntity storedUser = userRepository.save(userEntity);
		List<UserEntity> users = new ArrayList<UserEntity>();
		users.add(storedUser);
		role.setUsers(users);
		roleRepository.saveAndFlush(role);

		UserDto returnValue = tempConverter.userEntityToDto(storedUser);
		return returnValue;
	}

	@Override
	public UserDto getCurrentUser() {
		// TODO Auto-generated method stub
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userRepository.findById(authUser.getId()).get();
		UserDto returnValue = tempConverter.userEntityToDto(userEntity);
		return returnValue;
	}

	@Override
	public void suspendUser(Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserEntity> userOpt = userRepository.findById(userId);
		if (userOpt.isPresent()) {
			UserEntity userEntity = userOpt.get();
			userEntity.setEnabled((byte) 0);
			userRepository.saveAndFlush(userEntity);
		} else {
			Error error = new Error("Instace not exist!");
			throw new InstanceNotExistException(error);
		}
	}

	@Override
	public void reactivateUser(Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserEntity> userOpt = userRepository.findById(userId);
		if (userOpt.isPresent()) {
			UserEntity userEntity = userOpt.get();
			userEntity.setEnabled((byte) 1);
			userRepository.saveAndFlush(userEntity);
		} else {
			Error error = new Error("Instace not exist!");
			throw new InstanceNotExistException(error);
		}
	}

}
