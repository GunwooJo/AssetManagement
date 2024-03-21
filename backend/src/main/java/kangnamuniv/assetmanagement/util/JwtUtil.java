package kangnamuniv.assetmanagement.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Generate a secure key. Ideally, this key should be stored outside the source code,
    // in a configuration file or environment variable for better security.
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String login_id) {
        long expirationTime = 1000 * 60 * 30; // Token validity 30 minutes.

        return Jwts.builder()
                .setSubject(login_id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key) // Use the generated key
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            // This line will throw an exception if it is not a signed JWS (as expected)
            Jwts.parserBuilder()
                    .setSigningKey(key) // Set the same key as the one used while creating the token
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            // If any exception occurs while validating, consider the token to be invalid
            return false;
        }
    }

    // Method to extract login_id from token
    //토큰을 검증하고 검증이 완료되면 이 메서드를 통해 요청자의 이름을 얻어내고, Role, Permission 등 다양한 정보를 얻어서 특정 자료에 접근 가능하게 할 것인지 결정할 수 있다.
    public static String getLoginIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

