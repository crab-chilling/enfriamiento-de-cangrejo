package cpe.asi.cardforge.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils {

    public static String decodeJwtBody(String token) {
        return null;
    }

    public static String decodeJwtHeader(String token){
        return null;
    }

    public static String decodeJwtSignature(String token){
        return null;
    }

    public static Long getJwtIdFromBody(String token, Base64 base64) throws IOException {
        Map<String, Object> mapping = new ObjectMapper().readValue(base64.decode(token), HashMap.class  );
        return Long.parseLong(mapping.get("userId").toString());
    }
}
