package com.guestbook.Service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.guestbook.Web.dto.UserRegistrationDto;
import com.guestbook.model.User;

public interface UserService extends UserDetailsService {
	
	 public User save(UserRegistrationDto userRegistrationDto);
	 public List<User> listAll();
	 public User get(long id);
	 public void delete(long id);
	// public void save(User user);
	
}
