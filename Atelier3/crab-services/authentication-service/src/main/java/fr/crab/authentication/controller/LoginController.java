package fr.crab.authentication.controller;

import fr.crab.entity.Kuser;
import fr.crab.repository.AuthenticationRepository;
import fr.crab.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.io.Encoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Date;

@RestController
@Slf4j
public class LoginController {
    private AuthenticationManager authenticationManager;
    private AuthenticationRepository authenticationRepository;

    LoginController(AuthenticationManager authenticationManager, AuthenticationRepository authenticationRepository){
        this.authenticationManager = authenticationManager;
        this.authenticationRepository = authenticationRepository;
    }


    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Kuser kuser) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(kuser.getUserName(), kuser.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        if(authenticationResponse.isAuthenticated()){
            Kuser authenticatedUser = authenticationRepository.findByUserName(kuser.getUserName()).orElseThrow(() -> new UsernameNotFoundException("Username" + kuser.getUserName() + " does not exist"));

            String jwt = Jwts.builder().setSubject("login")
                    .claim("userId", authenticatedUser.getId())
                    .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64URL.decode(SecurityConstants.SECRET))
                    .compact();
            return ResponseEntity.ok(jwt);
        }
        return new ResponseEntity<String>("unauthorized", HttpStatus.UNAUTHORIZED);
    }
}