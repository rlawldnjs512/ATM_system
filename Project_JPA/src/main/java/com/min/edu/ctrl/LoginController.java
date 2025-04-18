package com.min.edu.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.service.JWTService;
import com.min.edu.vo.AccountCredentials;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController {

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
		UsernamePasswordAuthenticationToken creds = 
				new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
		Authentication auth = authenticationManager.authenticate(creds);
		
		String jwts = jwtService.generateToken(auth.getName());
		return ResponseEntity.ok()
				 			 .header(HttpHeaders.AUTHORIZATION, "Bearer "+jwts)
				 			 .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				 			 .build();
	}
	
}
