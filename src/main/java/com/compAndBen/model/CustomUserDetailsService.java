package com.compAndBen.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CustomUserDetailsService implements UserDetails {
    //it has value of each attribute along with the password
    //of the entity
    private String password="";
    private UserMaster user;
    public CustomUserDetailsService(UserMaster user)
    {
        this.user=user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserType> userTypes= new ArrayList<>();
        userTypes.add(user.getUserType());
        HashSet<SimpleGrantedAuthority> authorities=new HashSet<>();
        for(UserType usertypes:userTypes)
        {
            authorities.add(new SimpleGrantedAuthority(usertypes.getUserTypeName()));

        }
        //so I want string representation of the authorities granted to the
        //object and that is what I need for now and that is the necessary thing I feel to practice
        //up the things

        return authorities;

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.user.getEmailId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
