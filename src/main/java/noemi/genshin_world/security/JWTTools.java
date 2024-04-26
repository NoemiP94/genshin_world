package noemi.genshin_world.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import noemi.genshin_world.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    //method to create token
    public String createToken(User user){
        String role = String.valueOf(user.getRole());
        return Jwts.builder().subject(String.valueOf(user.getId()))
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *24* 7))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
