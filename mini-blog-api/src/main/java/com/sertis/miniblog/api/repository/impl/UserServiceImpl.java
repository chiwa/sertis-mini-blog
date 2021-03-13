package com.sertis.miniblog.api.repository.impl;



import com.sertis.miniblog.api.model.user.User;
import com.sertis.miniblog.api.repository.inf.UserRepository;
import com.sertis.miniblog.api.repository.inf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
		this.bcryptEncoder = bcryptEncoder;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.sertis.miniblog.api.model.user.User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public com.sertis.miniblog.api.model.user.User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public com.sertis.miniblog.api.model.user.User findById(int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public com.sertis.miniblog.api.model.user.User save(com.sertis.miniblog.api.model.user.User user) {
		com.sertis.miniblog.api.model.user.User newUser = new com.sertis.miniblog.api.model.user.User();
		System.out.println("===>>>> " + newUser.toString());
		newUser.setUsername(user.getUsername());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.save(newUser);
	}
}
