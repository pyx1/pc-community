package com.pccommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Autowired
    private RepositoryAuthenticationProvider authenticationProvider;

    @java.lang.Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/css/**").permitAll();
        http.authorizeRequests().antMatchers("/js/**").permitAll();
        http.authorizeRequests().antMatchers("/images/**").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().antMatchers("/logut").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/catalogo/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/catalogo/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/producto/**").permitAll();

        /* Reject the others */
        http.authorizeRequests().antMatchers("/admin/sadmin/**").hasAnyRole("SADMIN");
        http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        /* Log in*/
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("email");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/");
        http.formLogin().failureUrl("/login?error=error");

        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");


    }
    @java.lang.Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider);
    }
}
