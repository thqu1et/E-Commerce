package com.thqu1et.e_commerces.service.Implementation;

import com.thqu1et.e_commerces.config.JwtProvider;
import com.thqu1et.e_commerces.exception.UserException;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.repository.UserRepository;
import com.thqu1et.e_commerces.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository , JwtProvider jwtProvider){
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserService LoadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id");
    }

    @Override
    public User findUserProfileByJwt(String Jwt) throws UserException {

        String email = jwtProvider.getEmailFromToken(Jwt);
        User user = userRepository.findByEmail(email);

        if (user == null){
            throw new UserException("User not found with email");
        }

        return user;
    }
}
