package bg.tu.sofia.security.util;

import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.Constants;
import bg.tu.sofia.security.transfer.JwtUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * Class validates a given token by using the secret configured in the application
 */
@Component
public class JwtTokenValidator {

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public JwtUserDto parseToken(String token) {
        JwtUserDto u = null;

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(Constants.SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            u = new JwtUserDto();
            
			u.setId(Integer.parseInt((String) body.get("userId")));
			u.setUsername(body.getSubject());
			u.setRole((String) body.get("role"));
			u.setBlockId((String) body.get("blockId"));
			u.setRoomId((String) body.get("roomId"));
            
        } catch (JwtException e) {
            // Simply print the exception and null will be returned for the userDto
            e.printStackTrace();
        }
        return u;
    }
}
