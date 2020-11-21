package com.guestbook.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.guestbook.Repository.UserRepository;
import com.guestbook.Service.UserService;
import com.guestbook.Web.dto.UserRegistrationDto;
import com.guestbook.model.User;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/indexadmin")
	public String viewHomePage(Model model){
		List<User> listusers= userService.listAll();
		model.addAttribute("listusers",listusers);			
	 
		return "indexadmin";
	}
	
	@GetMapping("/new")
	public String createNewUser(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		return "newuser";
		
	}

	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") long id) {
	    ModelAndView mav = new ModelAndView("edituser");
	    User user = userService.get(id);
	    mav.addObject("user", user);
	     
	    return mav;
	}
	
	
	 @RequestMapping("/delete/{id}") public String
	  deleteProduct(@PathVariable(name = "id") long id) { userService.delete(id);
	  return "redirect:/"; }
	 
	 @GetMapping("/403")
	 public String error403() {
		 return "";
		 
	 }
	 
	
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
		public String saveUser(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {
		//	userService.save(user);
		 userService.save(userRegistrationDto);			 	 
		    return "redirect:/";
		}
	
	
	
}
