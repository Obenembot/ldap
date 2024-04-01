package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LdapConfig {

    @Value("${spring.ldap.urls}")
    private String ldapUrl;
    @Value("${spring.ldap.base}")
    private String ldapBase;
    @Value("${spring.ldap.username}")
    private String ldapUsername;
    @Value("${spring.ldap.password}")
    private String ldapPassword;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setUserDn(ldapUsername);
        contextSource.setBase(ldapBase);
        contextSource.setPassword(ldapPassword);
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
