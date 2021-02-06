package com.microsoft.oauth2.config;


import com.microsoft.oauth2.rest.ClientSecretResource;
import com.microsoft.oauth2.rest.SecurityResource;
import com.microsoft.oauth2.rest.UserRoleResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;


@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RequestContextFilter.class);
        register(ClientSecretResource.class);
        //配置restful package.
        register(SecurityResource.class);
        register(UserRoleResource.class);
    }
}