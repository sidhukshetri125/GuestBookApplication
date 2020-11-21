package com.guestbook.Web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.AttributesImpl;

@Controller
public class ContactController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@GetMapping("/contact")
	public String showContactForm() {
		return "contactform";
	}
	
	@PostMapping("/contact")
	public String submitContactForm(HttpServletRequest request ,
			@RequestParam("attachment") MultipartFile multiPartFile ) throws MessagingException, UnsupportedEncodingException{
		String fullname=request.getParameter("fullname");
		String email=request.getParameter("email");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		
		MimeMessage message =mailSender.createMimeMessage(); 
		MimeMessageHelper helper= new MimeMessageHelper(message,true);
		
		
		
		//SimpleMailMessage message = new SimpleMailMessage();
		
		
		String mailsubject=fullname+"has sent a message";
		
		String mailcontent="<p><b>Sender Name</b>" +fullname+"/p";		
		mailcontent+="<p><b>sender  E-mail:</b> "+email+"</p>";
		mailcontent+="<p><b>Subject :</b>"+subject+"/p";
		mailcontent+="<p><b>content :</b>"+content+"/p";
		mailcontent+="<hr><img src='cid:abc'/>";
		
		ClassPathResource resourse= new ClassPathResource("/static/abc");
		helper.addInline("abc", resourse);
		
		
		helper.setFrom("sidhukshetri125@gmail.com", "Sidhu Kshetri");
		helper.setTo("sidhukshetri8@gmail.com");
		helper.setSubject(mailsubject);
		helper.setText(mailcontent,true);
		
		if(!multiPartFile.isEmpty()) {
			
			String filename=StringUtils.cleanPath(multiPartFile.getOriginalFilename());
			
			InputStreamSource source =new InputStreamSource() {
				
				public InputStream getInputStream()throws IOException{
					return multiPartFile.getInputStream();
					
				}
				
			};
			helper.addAttachment(filename, source);
			
		}
		
		
		mailSender.send(message);		
		
		return "message";
		
		
	}

}
