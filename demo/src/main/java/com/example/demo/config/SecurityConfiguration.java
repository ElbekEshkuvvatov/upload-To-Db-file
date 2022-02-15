package com.example.demo.config;


import com.example.demo.security.JWTTokenProvider;
import com.example.demo.security.JwtConfigure;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private  final  JWTTokenProvider jwtTokenProvider;
    public SecurityConfiguration(@Lazy UserDetailsService userDetailsService, JWTTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @SneakyThrows
    @Bean
    public AuthenticationManager  authenticationManager(){
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .antMatchers("/api/attachment/").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/attachment/upload").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET,"/api/attachment/getFile/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/api/attachment/delete/**").hasRole("ADMIN")
                .antMatchers("/api/auth/register").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/users/**").permitAll()

 //               .antMatchers(HttpMethod.GET,"/api/users/getAll").hasRole("ADMIN")          // permitAll()
//                .antMatchers(HttpMethod.GET,"/api/users/getId/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT,"/api/users/put/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/api/users/delete/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigure(jwtTokenProvider));

        // .formLogin();
        //   .httpBasic();

    }


}
