package io.github.almeida.renato.todolistapi.infrastructure.security;

import io.github.almeida.renato.todolistapi.infrastructure.persistence.user.UserModel;
import io.github.almeida.renato.todolistapi.infrastructure.persistence.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final UserRepository userRepository;
    private final AuthTokenService tokenService;

    public AuthController(AuthenticationManager manager, UserRepository userRepository, AuthTokenService tokenService) {
        this.manager = manager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel.UserModelDTO authenticationDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        Authentication authenticate = manager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.generateToken(((AuthUser) authenticate.getPrincipal()));
        return ResponseEntity.ok(new AuthLoginResponse(token));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody UserModel.UserModelDTO authenticationDTO){

        try {
            if(this.userRepository.findByUsername(authenticationDTO.username()).isPresent()){
                return ResponseEntity.badRequest().build();
            }
            userRepository.save(
                    new UserModel(authenticationDTO.username(), new BCryptPasswordEncoder().encode(authenticationDTO.password()))
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    record AuthLoginResponse(String token){}
}
