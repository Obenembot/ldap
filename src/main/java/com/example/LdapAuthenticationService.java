package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LdapAuthenticationService {

    private Logger log = LoggerFactory.getLogger(LdapAuthenticationService.class);

    private LdapTemplate ldapTemplate;
    private PasswordEncoder passwordEncoder;

    public LdapAuthenticationService(final LdapTemplate ldapTemplate, PasswordEncoder passwordEncoder) {
        this.ldapTemplate = ldapTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(String username) {
        // You can customize this filter according to your LDAP structure
        String filter = "(sAMAccountName=" + username + ")";
        return ldapTemplate.authenticate("", filter, "");
    }

    public boolean authenticate(String username, String password) {
        String encodePassword = passwordEncoder.encode(password);
        log.info("Authentication: {} {} ", password, encodePassword);
        // You can customize this filter according to your LDAP structure
        String filter = "(sAMAccountName=" + username + ")";
        return ldapTemplate.authenticate("", filter, password);
    }
}
