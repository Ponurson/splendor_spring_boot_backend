package pl.coderslab.splendor_angular_connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.splendor_angular_connection.auth.SpringDataUserDetailsService;

@SpringBootApplication
public class SplendorAngularConnectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplendorAngularConnectionApplication.class, args);
    }

    @Configuration
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {


        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private SpringDataUserDetailsService customUserDetailsService;


        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(customUserDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.cors().and()
                    .httpBasic().and()
                    .authorizeRequests()
                    .antMatchers("/register").permitAll()
                    .anyRequest().hasRole("USER")
                    .and()
                    .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            .maximumSessions(1)
//                    .maxSessionsPreventsLogin(true);
            // @formatter:on
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }
}
