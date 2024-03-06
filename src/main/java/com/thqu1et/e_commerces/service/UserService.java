package com.thqu1et.e_commerces.service;

import com.thqu1et.e_commerces.exception.UserException;
import com.thqu1et.e_commerces.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public interface UserService {

    UserService LoadUserByUsername(String username) throws UsernameNotFoundException;

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String Jwt) throws UserException;

}
