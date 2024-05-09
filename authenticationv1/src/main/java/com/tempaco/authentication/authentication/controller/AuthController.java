package com.tempaco.authentication.authentication.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tempaco.authentication.authentication.dto.JwtAuthenticationResponse;
import com.tempaco.authentication.authentication.dto.SignInRequestDto;
import com.tempaco.authentication.authentication.dto.SignUpRequest;
import com.tempaco.authentication.authentication.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationService authenticationService;

	@PostMapping("/signup")
	public JwtAuthenticationResponse signup(@RequestBody SignUpRequest signUpRequest) {
		return authenticationService.signup(signUpRequest);
	}

	@PostMapping("/signin")
	public JwtAuthenticationResponse signin(@RequestBody SignInRequestDto signInRequestDto) {
		return authenticationService.signin(signInRequestDto);
	}

}
