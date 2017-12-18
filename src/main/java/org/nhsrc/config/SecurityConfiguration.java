package org.nhsrc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Value("${fa.secure}")
    private boolean isSecure;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/api/facility-assessment").permitAll();
        if (isSecure) {
            registry.antMatchers("/loginSuccess").hasAuthority("USER");
            permittedResources(new String[]{"checkpoint", "measurableElement", "standard", "areaOfConcern", "checklist", "assessmentTool", "assessmentType", "department", "facilityType", "facility", "district", "state"}, registry);
            String[] semiProtectedResources = {"checkpointScore", "facilityAssessment", "facilityAssessmentProgress"};
            permittedResourcesForOneDevice(semiProtectedResources, registry);
            permittedResourcesWithAuthority(semiProtectedResources, registry);

            registry
                    .anyRequest().authenticated().and().csrf().disable()
                    .formLogin().loginPage("/login").successForwardUrl("/loginSuccess").successHandler((request, response, authentication) -> {
                System.out.println("Login Successful");
            }).failureHandler((request, response, exception) -> {
                System.out.println("Login Failed");
            })
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .and().exceptionHandling();
        } else
            registry.antMatchers("/api/**").permitAll();
    }

    private void permittedResourcesWithAuthority(String[] patterns, ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        Arrays.stream(patterns).forEach(s -> {
            registry.antMatchers(String.format("/api/%s", s)).hasAuthority("USER");
        });
    }

    private void permittedResourcesForOneDevice(String[] patterns, ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        Arrays.stream(patterns).forEach(s -> {
            registry.antMatchers(String.format("/api/%s/search/lastModifiedByDeviceId", s)).permitAll();
        });
    }

    private void permittedResources(String[] patterns, ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        Arrays.stream(patterns).forEach(s -> {
            registry.antMatchers(String.format("/api/%s/**", s)).permitAll();
        });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/ext/**");
    }

}