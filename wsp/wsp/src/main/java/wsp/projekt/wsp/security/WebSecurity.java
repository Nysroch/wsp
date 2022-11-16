package wsp.projekt.wsp.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import wsp.projekt.wsp.constants.SecurityConstants;

@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; // do you remember we created a bean for that? here is the
															// usage

	@Override
	// here are some configurations, let's explain
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin() // we accpet all frames that are of the same origin (I recommend you
													// disable
				.and().cors().configurationSource(corsConfigurationSource()) // you can scroll down and read the cors
																				// config
				.and().csrf().disable().httpBasic().and().authorizeRequests().antMatchers("/error").permitAll()
				.antMatchers("/error/**").permitAll().antMatchers("/your Urls that dosen't need security/**")
				.permitAll().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll() // we are
																										// liberating
																										// the sign up
																										// url
				.anyRequest().permitAll() // here we say that any other request need to be authenticated
				.and().addFilter(new JWTAuthenticationFilter(authenticationManager())) // our filters are here
				.addFilter(new JWTAuthorizationFilter(authenticationManager())) // and our auth
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // we dont work with
																								// sessions, just jwt
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder); // here we show the
																								// class who will get
		System.out.println(bCryptPasswordEncoder.encode("asd"));
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

		corsConfiguration
				.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		source.registerCorsConfiguration("/**", corsConfiguration); // we permit any url after / execute http operations
																	// with our system

		return source;
	}
}