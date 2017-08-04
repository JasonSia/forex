package com.team2.forex.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
			throws Exception {
		auth.inMemoryAuthentication()
			.withUser("client").password("client").roles("USER")
			.and().withUser("client1").password("client1").roles("USER")
			.and().withUser("client2").password("client2").roles("USER")
			.and().withUser("client3").password("client3").roles("USER")
			.and().withUser("client4").password("client4").roles("USER")
			.and().withUser("admin").password("admin").roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/h2/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().headers().frameOptions().disable()
		.and().csrf().disable();
	}
}
