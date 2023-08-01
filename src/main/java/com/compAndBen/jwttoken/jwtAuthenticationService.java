package com.compAndBen.jwttoken;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class jwtAuthenticationService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("siliconbanksecret".equals(username)) {
            return new User(username, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());

        } else {
            throw new UsernameNotFoundException("The username is not found for the user"+username);

        }

    }
    //here I need to encrypt and decrypt the token
}

//we are creating the userDetails object and fetching the values thereon
//it will return the jwtToken to the user
//UserDetails provided us the hardcoded user class for manipulation