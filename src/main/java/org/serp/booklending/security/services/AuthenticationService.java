package org.serp.booklending.security.services;

import lombok.RequiredArgsConstructor;
import org.serp.booklending.model.User;
import org.serp.booklending.model.request.RegistrationRequest;
import org.serp.booklending.repository.RoleRepository;
import org.serp.booklending.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void register(RegistrationRequest request) {
        var userRole=roleRepository.findByName("USER")
                .orElseThrow(()-> new IllegalStateException("Role user isn't initialize"));
        var user= User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getFirstName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        //todo:sendEmailToUser
    }
}
