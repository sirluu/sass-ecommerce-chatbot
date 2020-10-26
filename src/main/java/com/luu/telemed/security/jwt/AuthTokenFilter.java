package com.luu.telemed.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.luu.telemed.security.services.AdminDetailsServiceImpl;
import com.luu.telemed.security.services.DoctorDetailsServiceImpl;
import com.luu.telemed.security.services.PatientDetailsServiceImpl;
import com.luu.telemed.security.services.UserDetailsImpl;

/**
 * 
 * @author HungLQ7130
 *
 */
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private DoctorDetailsServiceImpl doctorDetailsService;
	@Autowired
	private PatientDetailsServiceImpl patientDetailsService;
	@Autowired
	private AdminDetailsServiceImpl adminDetailsService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

		String jwt = parseJwt(request);
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			String username = jwtUtils.getUserNameFromJwtToken(jwt);
			String userType = request.getHeader("User-Type");
			LOGGER.info("::::::::: username: {}", username);

			UserDetails userDetails;
			switch (userType) {
			case UserDetailsImpl.PATIENT:
				userDetails = patientDetailsService.loadUserByUsername(username);
				break;
			case UserDetailsImpl.DOCTOR:
				userDetails = doctorDetailsService.loadUserByUsername(username);
				break;
			case UserDetailsImpl.ADMIN:
				userDetails = adminDetailsService.loadUserByUsername(username);
				break;
			default:
				userDetails = null;
			}
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		try {
			LOGGER.info("request forwarded " + request.getServletPath());
			filterChain.doFilter(request, response);
		} catch (IOException e) {
			LOGGER.error("Cannot forward to: {} >>> IOException: {}", request.getServletPath(), e.getMessage());
		} catch (ServletException e) {
			LOGGER.error("Cannot forward to: {} >>> ServletException: {}", request.getServletPath(), e.getMessage());
		}

	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
}
