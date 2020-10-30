package com.virtuallab.authentication;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandler extends SecurityContextLogoutHandler {

    {
        super.setClearAuthentication(true);
        super.setInvalidateHttpSession(true);
    }

}
