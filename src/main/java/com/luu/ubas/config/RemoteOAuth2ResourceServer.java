package com.luu.ubas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 
 * @author titchip
 *
 */
@Configuration
@EnableResourceServer
public class RemoteOAuth2ResourceServer extends ResourceServerConfigurerAdapter {

  @Value("${security.oauth2.resource.id}")
  private String resourceId;
  
  @Value("${security.oauth2.client.client-id}")
  private String clientId;
  
  @Value("${security.oauth2.client.client-secret}")
  private String clientSecret;
  
  @Value("${security.oauth2.client.access-token-uri}")
  private String accessTokenUri;
  
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(resourceId);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().authenticated().and().requestMatchers()
        .antMatchers("/api/**").and().cors();
  }

  @Bean
  public RemoteTokenServices remoteTokenServices() {
    RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
    remoteTokenServices.setCheckTokenEndpointUrl("http://216.194.165.205:5000/oauth/check_token");
    remoteTokenServices.setClientId(clientId);
    remoteTokenServices.setClientSecret(clientSecret);
    return remoteTokenServices;
  }
}
