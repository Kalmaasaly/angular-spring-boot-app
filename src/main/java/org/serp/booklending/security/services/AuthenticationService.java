package org.serp.booklending.security.services;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.serp.booklending.model.EmailTemplate;
import org.serp.booklending.model.Token;
import org.serp.booklending.model.User;
import org.serp.booklending.model.request.RegistrationRequest;
import org.serp.booklending.repository.RoleRepository;
import org.serp.booklending.repository.TokenRepository;
import org.serp.booklending.repository.UserRepository;
import org.serp.booklending.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    @Value("application.mailing.frontend.activation-url")
    private  String activationUrl;
    public void register(RegistrationRequest request) throws MessagingException {
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
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken=generateAndActivationToken(user);

        emailService.sendEmail(user.getEmail(),user.getFullName(), EmailTemplate.ACTIVATE_ACCOUNT,
                activationUrl,newToken,"Account Activation");

    }

    private String generateAndActivationToken(User user) {
        String generateToken=generateActivationCode(6);
        var token= Token.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generateToken;
    }

    private String generateActivationCode(int length) {
        String characters="0123456789";
        StringBuilder code=new StringBuilder();
        SecureRandom random=new SecureRandom();
        for (int j = 0; j < length; j++) {
            int index=random.nextInt(characters.length());
            code.append(characters.charAt(j));
        }
        return code.toString();
    }
}
