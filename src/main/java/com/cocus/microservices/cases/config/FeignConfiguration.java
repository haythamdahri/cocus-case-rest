package com.cocus.microservices.cases.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.Arrays;

/**
 * @author Haytham DAHRI
 */
@Configuration
public class FeignConfiguration {

    @Value("${auth-config.access-token-uri}")
    private String accessTokenUri;

    @Value("${auth-config.client-id}")
    private String clientId;

    @Value("${auth-config.client-secret}")
    private String clientSecret;

    @Value("${auth-config.scope}")
    private String scope;

    @Value("${auth-config.username}")
    private String username;

    @Value("${auth-config.password}")
    private String password;

    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    private OAuth2ProtectedResourceDetails resource() {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername(this.username);
        resourceDetails.setPassword(this.password);
        resourceDetails.setAccessTokenUri(accessTokenUri);
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(Arrays.asList(scope));
        return resourceDetails;
    }

}
