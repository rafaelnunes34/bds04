package com.devsuperior.bds04.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	private static final String[] URI_PUBLIC = { "/oauth/token","/h2-console/**" };
	private static final String[] URI_GET_PUBLIC = { "/cities/**","/events/**" };
	private static final String[] URI_POST_CLIENT = { "/events/**" };
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// TODO Auto-generated method stub
		super.configure(resources);
	} 

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.authorizeRequests()
			.antMatchers(URI_PUBLIC).permitAll()
			.antMatchers(HttpMethod.GET, URI_GET_PUBLIC).permitAll()
			.antMatchers(HttpMethod.POST, URI_POST_CLIENT).hasAnyRole("CLIENT", "ADMIN")
			.anyRequest().hasAnyRole("ADMIN");
	}
	
	

}
