package kangnamuniv.assetmanagement.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSigningKey() {
        if (secretKey == null) {
            throw new IllegalStateException("Secret key must not be null");
        }
        // Assumes your key is Base64-encoded; decode if necessary
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateToken(String login_id) {
        long expirationTime = 1000 * 60 * 30; // Token validity 30 minutes.

        return Jwts.builder()
                .setSubject(login_id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey()) // Use the generated key
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // This line will throw an exception if it is not a signed JWS (as expected)
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Set the same key as the one used while creating the token
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            // If any exception occurs while validating, consider the token to be invalid
            return false;
        }
    }

    //컨트롤러에서 Bearar가 붙은 토큰에서 Bearer를 떼어내고 검증.
    public boolean isTokenValid(String token) {
        // Assuming the token is a Bearer token, we remove the "Bearer " part.
        String actualToken = token.substring(7);

        // Validate the token first
        if (!validateToken(actualToken)) {
            return false;
        }
        return true;
    }

    //Bearer를 제외한 토큰 반환.
    public String getActualToken(String token) {
        return token.substring(7);
    }

    // Method to extract login_id from token
    //토큰을 검증하고 검증이 완료되면 이 메서드를 통해 요청자의 이름을 얻어내고, Role, Permission 등 다양한 정보를 얻어서 특정 자료에 접근 가능하게 할 것인지 결정할 수 있다.
    public String getLoginIdFromToken(String token) {
        String actualToken = getActualToken(token);
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(actualToken)
                .getBody()
                .getSubject();
    }
}

