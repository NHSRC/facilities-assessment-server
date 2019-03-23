package org.nhsrc.config;

import org.nhsrc.domain.security.Privilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class GunakWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    //    With constructor approach of auto-wiring it doesn't work
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String privilegesQuery;

    @Value("${fa.secure}")
    private boolean isSecure;

    private static Logger logger = LoggerFactory.getLogger(GunakWebSecurityConfigurerAdapter.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(privilegesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.
                authorizeRequests()
                .antMatchers("/", "/api/login", "/api/ping", "/api/users/first", "/api/error/throw").permitAll()
                .antMatchers(HttpMethod.POST, "/api/facility-assessment/checklist", "/api/facility-assessment/indicator", "/api/facility-assessment/**", "/api/facility-assessment").permitAll();

        permittedResources(new String[]{"checkpoint", "measurableElement", "standard", "areaOfConcern", "checklist", "assessmentToolMode", "assessmentTool", "assessmentType", "department", "facilityType", "facility", "district", "state", "indicatorDefinition"}, registry);
        registry.antMatchers(new String[]{"/api/currentUser", "/api/loginSuccess"}).hasRole(Privilege.USER_WITHOUT_PREFIX);
        if (isSecure) {
            String[] protectedResources = {"checkpointScore", "facilityAssessment", "facilityAssessmentProgress", "indicator", "users", "user", "facilityAssessmentMissingCheckpoint"};
            permittedResourcesForOneDevice(protectedResources, registry);
            permittedResourcesWithAuthority(protectedResources, registry);
            registry.antMatchers("/api/**").permitAll();
        } else {
            registry.antMatchers("/api/**").permitAll().and().csrf().disable();
        }

        handleLogin(registry);
    }

    private void handleLogin(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) throws Exception {
        registry.anyRequest().authenticated().and().csrf().disable()
                .formLogin().loginPage("/api/login").successHandler((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            logger.info("Login Successful");
        }).failureHandler((request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.error("Login Failed", exception);
        })
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .and().exceptionHandling();
    }

    private void permittedResourcesWithAuthority(String[] patterns, ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        Arrays.stream(patterns).forEach(s -> {
            registry.antMatchers(String.format("/api/%s", s)).hasRole(Privilege.USER_WITHOUT_PREFIX);
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
        web.ignoring().antMatchers("/ext/**", "/app/**");
    }

}