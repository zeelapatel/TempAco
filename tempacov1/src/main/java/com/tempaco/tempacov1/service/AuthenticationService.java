package com.tempaco.tempacov1.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tempaco.tempacov1.dto.JwtAuthenticationResponse;
import com.tempaco.tempacov1.dto.SignInRequestDto;
import com.tempaco.tempacov1.dto.SignUpRequest;
import com.tempaco.tempacov1.model.Role;
import com.tempaco.tempacov1.model.Token;
import com.tempaco.tempacov1.model.TokenType;
import com.tempaco.tempacov1.model.User;
import com.tempaco.tempacov1.repository.TokenRepository;
import com.tempaco.tempacov1.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final TokenRepository tokenRepository;

	public JwtAuthenticationResponse signup(SignUpRequest request) {
		var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.ROLE_USER)
				.build();

		user = userService.save(user);
		var jwt = jwtService.generateToken(user);
		revokeAllToken(user);
		saveUserToken(user, jwt);
		return JwtAuthenticationResponse.builder().token(jwt).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).build();
	}

	public JwtAuthenticationResponse signin(SignInRequestDto request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
		var jwt = jwtService.generateToken(user);
		revokeAllToken(user);
		saveUserToken(user, jwt);




		return JwtAuthenticationResponse.builder().token(jwt).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).build();
	}

	private void revokeAllToken(User user){
		var validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if(validTokens.isEmpty()) {return;}
		validTokens.forEach(t ->{
			t.setExpired(true);
			t.setRevoked(true);
		});
		tokenRepository.saveAll(validTokens);
	}
	
	private void saveUserToken(User user, String jwt) {
		var token = Token.builder().user(user).token(jwt).tokenType(TokenType.BEARER).expired(false).revoked(false)
				.build();
		tokenRepository.save(token);
	}
}
