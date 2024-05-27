package cpe.asi.cardforge.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.spec.KeySpec;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = (5 * 60 * 1000);
    public static final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
}
