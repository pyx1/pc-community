package com.pccommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(value = 1)
public class CustomRestSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    public RepositoryAuthenticationProvider rAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests().antMatchers("/api/login").authenticated();
        /* User */
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/products/**").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/products/**/units").hasRole("USER");
        http.authorizeRequests().antMatchers("/api/orders").hasRole("USER");
        http.authorizeRequests().antMatchers("/api/reviews").hasRole("USER");
        http.authorizeRequests().antMatchers("/api/cart").hasRole("USER");


        /* Admin */
        http.authorizeRequests().antMatchers("/api/admin/**").hasRole("ADMIN");
        
        /* Else unlocked */
        http.authorizeRequests().anyRequest().permitAll();


        http.httpBasic();

        http.logout().logoutSuccessHandler((rq, rs, a) -> {}); //Anonimous function to avoid redirect


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(rAuthenticationProvider);
    }
    
}
