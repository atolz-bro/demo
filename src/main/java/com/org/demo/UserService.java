package com.org.demo;

import com.org.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

@Service
public class UserService {

    private LdapTemplate ldapTemplate;

    public void addUser(User user) {
        // Construct the DN (Distinguished Name) of the new user
        Name dn = buildDn(user.getLastName());

        // Create LDAP attributes for the new user
        DirContextAdapter context = new DirContextAdapter(dn);
        context.setAttributeValues("objectclass", new String[]{"inetOrgPerson", "organizationalPerson", "person", "top"});
        context.setAttributeValue("cn", user.getCommonName());
        context.setAttributeValue("userPassword", user.getPassword()); // Store password encrypted in a real scenario!

        // Save the user to LDAP
        ldapTemplate.bind(context);
    }

    private Name buildDn(String sn) {
        return LdapUtils.newLdapName("sn="+sn+",ou=users,ou=system");
    }
}
