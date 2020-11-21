package com.guestbook.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.guestbook.Repository.UserRepository;
import com.guestbook.model.User;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 // User user = userRepository.getUserByUsername(email);
		User user = userRepository.findByEmail(username);	   
	        if (user == null){
	            throw new UsernameNotFoundException("Invalid username or password.");
	        }
	        
	        return new MyUserDetails(user);
			/*
			 * return new
			 * org.springframework.security.core.userdetails.User(user.getEmail(),
			 * user.getPassword(), mapRolesToAuthorities(user.getRoles()));
			 */
	}

}
