package com.guestbook.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.ExcludeDefaultListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.guestbook.Repository.UserRepository;
import com.guestbook.Web.dto.UserRegistrationDto;
import com.guestbook.model.Role;
import com.guestbook.model.User;

@Service
public class UserServiceImpl implements UserService {	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	 
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto userRegistrationDto) {
		
		User user = new User(userRegistrationDto.getFirstname(),
				             userRegistrationDto.getLastname(),				             
				             userRegistrationDto.getEmail(),				             
				             passwordEncoder.encode(userRegistrationDto.getPassword()) ,Arrays.asList(new Role("ROLE_USER")));
		 
		return userRepository.save(user) ;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  User user = userRepository.findByEmail(username);
	        if (user == null){
	            throw new UsernameNotFoundException("Invalid username or password.");
	        }
	        return new org.springframework.security.core.userdetails.User(user.getEmail(),
	                user.getPassword(),
	                mapRolesToAuthorities(user.getRoles()));
	    }	 
	
	 private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
	        return roles.stream()
	                .map(role -> new SimpleGrantedAuthority(role.getName()))
	                .collect(Collectors.toList());
	    }
	 
	 //CRUD Opertions
	 public List<User> listAll() {
	        return userRepository.findAll();
	    }
	 
		/*
		 * public void save(User user) { userRepository.save(user);
		 * 
		 * }
		 */
	 public User get(long id) {
		 return userRepository.findById(id).get();
	 }
	 
	 public void delete(long id) {
		 userRepository.deleteById(id);
	    }
	 
}
