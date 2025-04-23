package com.ssm.config.swagger.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SwaggerService {

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}