package com.cocus.microservices.cases.facades;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Haytham DAHRI
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String extractUsernameFromAuthentication() {
        Authentication authentication = this.getAuthentication();
        if( authentication.getPrincipal() instanceof KeycloakPrincipal ) {
            return ((KeycloakPrincipal)authentication.getPrincipal()).getKeycloakSecurityContext().getToken().getPreferredUsername();
        }
        return authentication.getName();
    }

}
