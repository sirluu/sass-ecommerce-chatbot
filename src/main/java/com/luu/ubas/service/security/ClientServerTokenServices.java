package com.luu.ubas.service.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.*;

/**
 * 
 * @author HungLQ7130
 *
 */

public class ClientServerTokenServices implements ResourceServerTokenServices {

  protected final Log LOGGER = LogFactory.getLog(getClass());

  private static final String[] PRINCIPAL_KEYS = new String[] {"user", "username", "userid", "user_id", "login", "id", "name"};

  private final String userInfoEndpointUrl;

  private final String clientId;

  private OAuth2RestOperations restTemplate;

  private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

  private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

  public ClientServerTokenServices(String userInfoEndpointUrl, String clientId) {
    this.userInfoEndpointUrl = userInfoEndpointUrl;
    this.clientId = clientId;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public void setRestTemplate(OAuth2RestOperations restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
    this.authoritiesExtractor = authoritiesExtractor;
  }

  @Override
  public OAuth2Authentication loadAuthentication(String accessToken) {
    Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken);
    if (map.containsKey("error")) {
      LOGGER.error("Get user info returned error >>> " + map.get("error"));
    }
    return extractAuthentication(map);
  }

  @Override
  public OAuth2AccessToken readAccessToken(String accessToken) {
    LOGGER.error("Not supported: read access token");
    return null;
  }
  
  private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
    Object principal = getPrincipal(map);
    OAuth2Request request = getRequest(map);
    if (request == null) {
      LOGGER.error("Extract authentication from access token error");
      return null;
    }
    List<GrantedAuthority> authorities = this.authoritiesExtractor.extractAuthorities(map);
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
    token.setDetails(map);
    return new OAuth2Authentication(request, token);
  }

  private Object getPrincipal(Map<String, Object> map) {
    for (String key : PRINCIPAL_KEYS) {
      if (map.containsKey(key)) {
        return map.get(key);
      }
    }
    return "unknown";
  }

  private OAuth2Request getRequest(Map<String, Object> request) {
    try {
      Set<String> authorities = new HashSet<>();
      authorities.add((String) request.get("authorities"));
      return new OAuth2Request(null, clientId, null, true, authorities, null, null, null, null);
    } catch (Exception e) {
      LOGGER.error("Get request from access token error >>> " + e.getLocalizedMessage());
      return null;
    }
  }

  private Map<String, Object> getMap(String path, String accessToken) {
    this.LOGGER.debug("Getting user info from: " + path);
    try {
      OAuth2RestOperations restTemplate = this.restTemplate;
      if (restTemplate == null) {
        BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
        resource.setClientId(this.clientId);
        resource.setAccessTokenUri(accessToken);
        restTemplate = new OAuth2RestTemplate(resource);
      }
      OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext().getAccessToken();
      if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
        token.setTokenType(this.tokenType);
        restTemplate.getOAuth2ClientContext().setAccessToken(token);
      }
      HttpEntity<Object> entity = new HttpEntity<>(getHeaders());
      return restTemplate.exchange(path, HttpMethod.POST, entity, Map.class).getBody();
    } catch (Exception ex) {
      LOGGER.error("Could not fetch user details >>> " + ex.getLocalizedMessage());
      return Collections.<String, Object>singletonMap("error", "Could not fetch user details");
    }
  }

  public HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }
}
