package com.vegapp.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vegapp.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracionVegapp extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public UsuarioService usuarioService;
	
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{
		auth
			.userDetailsService(usuarioService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
					.antMatchers("/admin/*").hasRole("ADMIN")
					.antMatchers("/css/*","/js/*","/img/*","/**")
					.permitAll()
			.and().formLogin()
					.loginPage("/login")
							.loginProcessingUrl("/logincheck")
							.usernameParameter("username")
							.passwordParameter("password")
							.defaultSuccessUrl("/inicio")
							.permitAll()
					.and().logout()
							.logoutUrl("/logout")
							.logoutSuccessUrl("/")
							.permitAll().and().csrf().disable();
	}
}
