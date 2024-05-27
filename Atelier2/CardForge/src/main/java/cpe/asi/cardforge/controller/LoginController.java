package cpe.asi.cardforge.controller;

import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.repository.UserRepository;
import cpe.asi.cardforge.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@Slf4j
public class LoginController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    LoginController(AuthenticationManager authenticationManager, UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody Kuser kuser) {
        log.info(kuser.toString());
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(kuser.getUserName(), kuser.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        if(authenticationResponse.isAuthenticated()){
            Kuser authenticatedUser = userRepository.findByUserName(kuser.getUserName()).orElseThrow(() -> new UsernameNotFoundException("Username" + kuser.getUserName() + " does not exist"));
            String jwt = Jwts.builder().setSubject("login")
                    .claim("userId", authenticatedUser.getId())
                    .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(SecurityConstants.SECRET))
                    .compact();
            return ResponseEntity.ok(jwt);
        }
        return new ResponseEntity<String>("unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
