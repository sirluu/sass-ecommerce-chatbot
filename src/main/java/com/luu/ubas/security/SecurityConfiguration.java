package com.luu.ubas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.luu.ubas.security.AuthProviders.AdminAuthenticationProvider;
import com.luu.ubas.security.AuthProviders.DoctorAuthenticationProvider;
import com.luu.ubas.security.AuthProviders.PatientAuthenticationProvider;
import com.luu.ubas.security.jwt.AuthEntryPointJwt;
import com.luu.ubas.security.jwt.AuthTokenFilter;
import com.luu.ubas.security.services.AdminDetailsServiceImpl;
import com.luu.ubas.security.services.DoctorDetailsServiceImpl;
import com.luu.ubas.security.services.PatientDetailsServiceImpl;

/**
 * 
 * @author titchip
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  AdminDetailsServiceImpl adminDetailsService;
  @Autowired
  DoctorDetailsServiceImpl doctorDetailsService;
  @Autowired
  PatientDetailsServiceImpl patientDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder.authenticationProvider(getAdminAuthenticationProvider());
    authenticationManagerBuilder.authenticationProvider(getDoctorAuthenticationProvider());
    authenticationManagerBuilder.authenticationProvider(getPatientAuthenticationProvider());

  }

  // @Bean
  // @Override
  // public AuthenticationManager authenticationManagerBean() throws Exception {
  // return super.authenticationManagerBean();
  // }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()

        .antMatchers("/api/auth/**").permitAll().antMatchers("/api/test/**").permitAll()
        .antMatchers("/doctors/**").permitAll().antMatchers("/patients/**").permitAll()
        .antMatchers("/clinics/**").permitAll().antMatchers("/**").permitAll()
        .antMatchers("/socket**").permitAll()
        // just to reload.
        .anyRequest().authenticated();

    http.addFilterBefore(authenticationJwtTokenFilter(),
        UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // Allow swagger to be accessed without authentication
    web.ignoring().antMatchers("/v2/api-docs")//
        .antMatchers("/swagger-resources/**")//
        .antMatchers("/swagger-ui.html")//
        .antMatchers("/configuration/**")//
        .antMatchers("/webjars/**")//
        .antMatchers("/public");

  }

  @Bean
  public AdminAuthenticationProvider getAdminAuthenticationProvider() {
    AdminAuthenticationProvider dao = new AdminAuthenticationProvider();
    dao.setUserDetailsService(adminDetailsService);
    dao.setPasswordEncoder(passwordEncoder());
    return dao;
  }

  @Bean
  public DoctorAuthenticationProvider getDoctorAuthenticationProvider() {
    DoctorAuthenticationProvider dao = new DoctorAuthenticationProvider();
    dao.setUserDetailsService(doctorDetailsService);
    dao.setPasswordEncoder(passwordEncoder());
    return dao;
  }

  @Bean
  public PatientAuthenticationProvider getPatientAuthenticationProvider() {
    PatientAuthenticationProvider dao = new PatientAuthenticationProvider();
    dao.setUserDetailsService(patientDetailsService);
    dao.setPasswordEncoder(passwordEncoder());
    return dao;
  }
}
