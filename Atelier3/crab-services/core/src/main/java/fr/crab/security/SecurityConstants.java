package fr.crab.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = (5 * 60 * 1000);
    public static final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
}
