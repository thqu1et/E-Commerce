package com.thqu1et.e_commerces.controller;

import com.thqu1et.e_commerces.Response.AuthResponse;
import com.thqu1et.e_commerces.config.JwtProvider;
import com.thqu1et.e_commerces.exception.UserException;
import com.thqu1et.e_commerces.model.Cart;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.repository.UserRepository;
import com.thqu1et.e_commerces.request.LoginRequest;
import com.thqu1et.e_commerces.service.CartService;
import com.thqu1et.e_commerces.service.Implementation.CustomUserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    private CustomUserServiceImpl customUserServiceImpl;

    private CartService cartService;

    public AuthController(UserRepository userRepository ,JwtProvider jwtProvider ,PasswordEncoder passwordEncoder , CustomUserServiceImpl customUserServiceImpl, CartService cartService){
        this.userRepository = userRepository;
        this.jwtProvider =jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.customUserServiceImpl = customUserServiceImpl;
        this.cartService = cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null){
            throw new UserException("User is already User with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);


        User savedUser = userRepository.save(createdUser);
        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail() , savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignUp success");

        return new ResponseEntity<AuthResponse>(authResponse , HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username , password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignIn success");

        return new ResponseEntity<AuthResponse>(authResponse , HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);
        if (userDetails == null){
            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncoder.matches(password , userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }


        return new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
    }
}
