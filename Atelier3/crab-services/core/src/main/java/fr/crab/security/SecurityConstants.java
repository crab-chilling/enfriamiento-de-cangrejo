package fr.crab.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 3600000L;
    public static final String SECRET = "fd5f8f240321cce301ac27daacd4205b5e2fe0b72d12762183d7000977eaa2db";
}
