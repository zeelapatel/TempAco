package com.tempaco.authentication.dto;

import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	String firstName;
	String lastName;
	String email;
	String password;
}
