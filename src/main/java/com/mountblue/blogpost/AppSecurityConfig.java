package com.mountblue.blogpost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    /*
    //this is in memory
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        List<UserDetails> userDetails = new ArrayList<>();
        userDetails.add(User.withDefaultPasswordEncoder().username("umakant").password("root").roles("USER").build());
        return  new InMemoryUserDetailsManager(userDetails);
    }*/

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authprovider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        //provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    //for custom login page
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/adminlogin").permitAll()
                .and()
                .formLogin()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll();
        /*.loginPage("/adminlogin").permitAll()*/
    }
}
