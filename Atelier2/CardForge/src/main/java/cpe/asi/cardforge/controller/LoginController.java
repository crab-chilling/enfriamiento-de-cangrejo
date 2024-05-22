package cpe.asi.cardforge.controller;

import cpe.asi.cardforge.entity.Kuser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/user-card")
    public ResponseEntity login(@RequestBody Kuser kuser) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(kuser.getUserName(), kuser.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        return ResponseEntity.ok(authenticationRequest);
    }

}
