package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LdapAuthenticationService {

    private Logger log = LoggerFactory.getLogger(LdapAuthenticationService.class);

    private LdapTemplate ldapTemplate;
    @Value("${spring.ldap.base}")
    private String ldapBase;
    private PasswordEncoder passwordEncoder;

    public LdapAuthenticationService(final LdapTemplate ldapTemplate, PasswordEncoder passwordEncoder) {
        this.ldapTemplate = ldapTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean authenticate(String username, String password) {
        String encodePassword = passwordEncoder.encode(password);
        log.info("Authentication: {} {} ", password, encodePassword);
        // You can customize this filter according to your LDAP structure
        String filter = "(sAMAccountName=" + username + ")";
        boolean authenticate = ldapTemplate.authenticate(ldapBase, filter, password);
        if (!authenticate) {
            authenticate = ldapAuthenticate(username, password);
        }
        return authenticate;
    }

    private boolean ldapAuthenticate(String username, String password) {
        return ldapTemplate.authenticate(ldapBase, "mail=" + username, password);
    }
}
