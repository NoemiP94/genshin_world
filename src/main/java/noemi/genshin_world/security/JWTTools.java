package noemi.genshin_world.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import noemi.genshin_world.entities.User;
import noemi.genshin_world.exceptions.UnauthorizedException;
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

    //method to verify token
    public void verifyToken(String token){
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        }catch(Exception e){
            throw new UnauthorizedException("There are problems with token!");
        }
    }

    //method to extract ID from token
    public String extractIdFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
