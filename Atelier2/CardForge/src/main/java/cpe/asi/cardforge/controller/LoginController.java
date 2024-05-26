package cpe.asi.cardforge.controller;

import cpe.asi.cardforge.entity.Kuser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Slf4j
public class LoginController {
    private static final long EXPIRATION_TIME = 10L;
    private static final String SECRET = "TEST";
    private AuthenticationManager authenticationManager;

    LoginController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody Kuser kuser) {
        log.info(kuser.toString());
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(kuser.getUserName(), kuser.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        if(authenticationResponse.isAuthenticated()){
            String jwt = Jwts.builder().setSubject(kuser.getUserName() +
                    kuser.getPassword() +
                    kuser.getRole() +
                    kuser.getFirstName() +
                    kuser.getLastName() +
                    kuser.getEmailAddress())
                    .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode(SECRET))
                    .claim("roles", kuser.getRole())
                    .compact();
            return ResponseEntity.ok(jwt);
        }
        return new ResponseEntity<String>("unauthorized", HttpStatus.UNAUTHORIZED);
    }

}
