package com.guestbook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guestbook.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);	
  
  // @Query("SELECT u FROM User u WHERE u.username = :username")
  // public User getUserByUsername(@Param("email") String email);
	   
   

}
