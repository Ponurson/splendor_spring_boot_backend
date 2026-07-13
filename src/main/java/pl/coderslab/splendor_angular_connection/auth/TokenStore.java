package pl.coderslab.splendor_angular_connection.auth;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Issues and validates opaque session tokens. Replaces sending base64(username:password)
 * (HTTP Basic) from the client on every request.
 */
// ponytail: in-memory store, no expiry and lost on restart. Add TTL/eviction or move to
// signed JWTs (app.jwt.signing-secret already reserved) when multi-instance or persistence matters.
@Component
public class TokenStore {

    private final Map<String, String> tokensToUsernames = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();

    public String issue(String username) {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        tokensToUsernames.put(token, username);
        return token;
    }

    public String usernameFor(String token) {
        return token == null ? null : tokensToUsernames.get(token);
    }

    public void revoke(String token) {
        if (token != null) {
            tokensToUsernames.remove(token);
        }
    }
}
