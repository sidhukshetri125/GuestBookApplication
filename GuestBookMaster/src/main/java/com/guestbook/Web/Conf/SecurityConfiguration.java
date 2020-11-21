package com.guestbook.Web.Conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.guestbook.Service.UserDetailServiceImpl;
import com.guestbook.Service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	UserService userService;
	
	 @Bean
	    public UserDetailsService userDetailsService() {
	        return new UserDetailServiceImpl();
	    }
	 
	 @Bean
	 public BCryptPasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	
		/*
		 * @Override protected void configure(HttpSecurity http) throws Exception {
		 * http.authorizeRequests().antMatchers( "/registration**", "/js/**", "/css/**",
		 * "/img/**", "/webjars/**").permitAll() .anyRequest().authenticated() .and()
		 * .formLogin() .loginPage("/login") .permitAll() .and() .logout()
		 * .invalidateHttpSession(true) .clearAuthentication(true)
		 * .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		 * .logoutSuccessUrl("/login?logout") .permitAll(); }
		 */
	 
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	            .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
	            .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
	            .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
	            .antMatchers("/delete/**").hasAuthority("ADMIN")
	            .anyRequest().authenticated()
	            .and()
	            .formLogin().permitAll()
	            .and()
	            .logout().permitAll()
	            .and()
	            .exceptionHandling().accessDeniedPage("/403")
	            ;
	    }
	
		 
		  @Bean
		  public DaoAuthenticationProvider authenticationProvider(){
		  DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		  auth.setUserDetailsService(userService);
		 // auth.setUserDetailsService(userDetailsService);
		  auth.setPasswordEncoder(passwordEncoder());
		  return auth; }
		  

		
		 @Override
		 protected void configure(AuthenticationManagerBuilder auth) throws
		  Exception { auth.authenticationProvider(authenticationProvider()); }
		 
}
