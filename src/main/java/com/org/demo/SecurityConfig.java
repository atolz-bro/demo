package com.org.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().fullyAuthenticated()
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth,BaseLdapPathContextSource baseLdapPathContextSource) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("CN={0},OU=doctors,DC=health,DC=corp")
                .contextSource().managerDn("CN=doc2,OU=doctors,DC=health,DC=corp").managerPassword("Elijah2001_AD")
                .url("ldap://16.171.57.3:389/DC=health,DC=corp")
                .and()
                .passwordCompare()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .passwordAttribute("description");
    }

  /*@Bean
    public LdapTemplate ldapTemplate(){
        return new LdapTemplate(contextSource());
    }

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        String local = "ldap://localhost:10389";
        String remote = "ldap://16.171.57.3:389";
        ldapContextSource.setUrl(remote);
        ldapContextSource.setUserDn("CN=doc2,OU=doctors,DC=health,DC=corp");
        ldapContextSource.setPassword("Elijah2001_AD");

        return ldapContextSource;
    }*/

    /*@Bean
    AuthenticationManager authenticationManager(BaseLdapPathContextSource baseLdapPathContextSource){
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(baseLdapPathContextSource);
        String dn = "CN={0},OU=doctors,DC=health,DC=corp";
        String localDn = "sn={0},ou=users,ou=system";
        factory.setUserDnPatterns(dn);
        return factory.createAuthenticationManager();
    }*/
}
