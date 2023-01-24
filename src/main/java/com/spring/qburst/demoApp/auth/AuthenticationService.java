package com.spring.qburst.demoApp.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.qburst.demoApp.config.JwtService;
import com.spring.qburst.demoApp.user.Role;
import com.spring.qburst.demoApp.user.User;
import com.spring.qburst.demoApp.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		
		var userObj = User.builder().firstName(request.getFirstName())
						.lastName(request.getLastName())
						.email(request.getEmail())
						.password(passwordEncoder.encode(request.getPassword()))
						.role(Role.USER)
						.build();
		
		userRepository.save(userObj);
		//to return authenticationresponse that contain jwttoken, use jwtservice to generate token.
		var jwtToken = jwtService.generateToken(userObj);
		
		return AuthenticationResponse.builder().token(jwtToken).build();
		
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		//if username n password correct generate token n return.
		var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Username not found !!!"));
		
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder().token(jwtToken).build();
		
	}
}
