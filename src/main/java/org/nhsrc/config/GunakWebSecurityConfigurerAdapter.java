package org.nhsrc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity
public class GunakWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    //    With constructor approach of auto-wiring it doesn't work
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

    private static Logger logger = LoggerFactory.getLogger(GunakWebSecurityConfigurerAdapter.class);

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
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/ping").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/app/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/facility-assessment/checklist").permitAll()
                .antMatchers(HttpMethod.POST, "/api/facility-assessment/indicator").permitAll()
                .antMatchers(HttpMethod.POST, "/api/facility-assessment/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/facility-assessment").permitAll();
        permittedResources(new String[]{"checkpoint", "measurableElement", "standard", "areaOfConcern", "checklist", "assessmentToolMode", "assessmentTool", "assessmentType", "department", "facilityType", "facility", "district", "state", "indicatorDefinition"}, registry);

        registry.anyRequest().authenticated().and().csrf().disable()
                .formLogin().loginPage("/api/login").successHandler((request, response, authentication) -> {
            logger.info("Login Successful");
        }).failureHandler((request, response, exception) -> {
            logger.info("Login Failed");
        })
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .and().exceptionHandling();

        if (isSecure) {
            registry.antMatchers("/api/loginSuccess").hasAuthority("USER");
            String[] semiProtectedResources = {"checkpointScore", "facilityAssessment", "facilityAssessmentProgress", "indicator"};
            permittedResourcesForOneDevice(semiProtectedResources, registry);
            permittedResourcesWithAuthority(semiProtectedResources, registry);
            permittedResourcesWithAuthority(new String[]{"/api/currentUser"}, registry);
        } else {
            registry.antMatchers("/api/**").permitAll().and().csrf().disable();
        }
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
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/ext/**");
    }

}