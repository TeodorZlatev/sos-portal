package bg.tu.sofia.security.util;

import org.springframework.stereotype.Component;

import bg.tu.sofia.security.transfer.JwtUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * convenience class to generate a token for testing your requests.
 * Make sure the used secret here matches the on in your application.yml
 */
@Component
public class JwtTokenGenerator {

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(JwtUserDto u, String secret) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getId() + "");
        claims.put("role", u.getRole());
        claims.put("blockId", u.getBlockId() + "");
        claims.put("roomId", u.getRoomId() + "");

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
