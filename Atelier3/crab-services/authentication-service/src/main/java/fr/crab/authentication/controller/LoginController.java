package fr.crab.authentication.controller;

import fr.crab.dto.UserDTO;
import fr.crab.entity.Kuser;
import fr.crab.repository.AuthenticationRepository;
import fr.crab.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.io.Encoders;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;

@RestController("/")
@Slf4j
public class LoginController {
    private AuthenticationManager authenticationManager;
    private AuthenticationRepository authenticationRepository;

    private ModelMapper modelMapper;

    LoginController(AuthenticationManager authenticationManager, AuthenticationRepository authenticationRepository){
        this.authenticationManager = authenticationManager;
        this.authenticationRepository = authenticationRepository;
        this.modelMapper = new ModelMapper();
    }


    @PostMapping(value = "/login")
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

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        Kuser user = authenticationRepository.findByUserName(userDTO.getUserName()).orElse(null);
        if(user != null){
            return new ResponseEntity<String>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        authenticationRepository.save(modelMapper.map(userDTO, Kuser.class));
        return new ResponseEntity<String>("User created", HttpStatus.CREATED);
    }
}
