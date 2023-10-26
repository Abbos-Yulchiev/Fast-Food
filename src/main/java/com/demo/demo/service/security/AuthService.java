package com.demo.demo.service.security;

import com.demo.demo.core.security.JwtProvider;
import com.demo.demo.dto.security.LoginDTO;
import com.demo.demo.dto.user.UserGetDTO;
import com.demo.demo.entity.User;
import com.demo.demo.repository.user.UserRepository;
import com.demo.demo.response.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository repository, AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public Data<String> login(LoginDTO loginDTO) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getUsername());
            if (user.isAccountNonLocked()
                    && user.isAccountNonExpired()
                    && user.isCredentialsNonExpired()
                    && !user.isDeleted()) {
                return new Data<>(token);
            } else {
                return new Data<>("User expired");
            }
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Username or password is wrong!");
        }
    }


    public ResponseEntity<Data<UserGetDTO>> authToken(User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Data<>(new UserGetDTO()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Data<>(new UserGetDTO()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found!"));
    }
}