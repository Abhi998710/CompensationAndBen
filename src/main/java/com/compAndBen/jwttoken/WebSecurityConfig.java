package com.compAndBen.jwttoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private jwtRequestFilter RequestFilter;

    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private jwtAuthEntry authEntry;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception
    {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passEncoder());



    }

    @Bean
    public PasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws  Exception{
        return super.authenticationManagerBean();
    }

    public void configure(HttpSecurity security) throws Exception
    {


        security.csrf().disable().authorizeHttpRequests().antMatchers("/authenticate")
                .permitAll().anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(authEntry).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //This ensures that the user's state is not stored in the session and a new session is not created for each request.

        security.addFilterBefore(RequestFilter, UsernamePasswordAuthenticationFilter.class);

    }




}