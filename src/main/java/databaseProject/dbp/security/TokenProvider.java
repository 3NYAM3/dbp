package databaseProject.dbp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenProvider {
    @Value("${SECURITY_KEY}")
    private String SECURITY_KEY;

    public String create(String email) {
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(exprTime)
                .compact();
    }

    public String validate(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECURITY_KEY)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return claims.getSubject();
    }
}
