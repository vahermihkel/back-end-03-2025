package ee.mihkel.veebipood.security;

import ee.mihkel.veebipood.dto.AuthToken;
import ee.mihkel.veebipood.entity.Person;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("MegaHyperSuperSecretKeyThatNobodyInTheWholeWorldKnows"));

    // {token: "ads13-1dasd-asd231", expiration: 2025-04-24}
    public AuthToken getToken(Person person) {
        AuthToken token = new AuthToken();
        Date today = new Date();
        Date expiration = new Date(today.getTime() + 2 * 60 * 60 * 1000); // 60 minutit
        token.setExpiration(expiration);



        String jwtToken = Jwts.builder()
                .signWith(key)
                .issuer("Meie_ettevõtte_nimi")
                .subject(person.getEmail())
                .claim("role", person.getRole())
                .expiration(expiration)
                .compact();
        token.setToken(jwtToken);
        return token;
    }

    public Claims parseToken(String authHeader) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(authHeader)
                .getPayload();
    }
}
