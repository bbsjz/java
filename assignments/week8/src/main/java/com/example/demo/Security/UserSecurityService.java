package com.example.demo.Security;

import com.example.demo.Entity.Role;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.demo.Entity.User user=userService.getUser(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("user "+username+" is not found");
        }
        List<GrantedAuthority> authorityList=new ArrayList<>();
        for(Role role:user.getData())
        {
            for(String auth:role.getAuthorities())
            {
                authorityList.add(new SimpleGrantedAuthority(auth));
            }
        }
        return User.builder().
                username(username).
                password(new BCryptPasswordEncoder().encode(user.getPassword())).
                authorities(authorityList.toArray(new GrantedAuthority[authorityList.size()])).
                build();
    }
}
