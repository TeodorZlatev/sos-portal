package bg.tu.sofia.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be found in the request header
 */
public class JwtTokenMissingException extends AuthenticationException {

	private static final long serialVersionUID = -5961368975688010965L;

	public JwtTokenMissingException(String msg) {
        super(msg);
    }
}
