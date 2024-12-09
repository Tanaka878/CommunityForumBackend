package org.example.communityforumapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private final String SECRET_KEY ="";
    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken,Claims::getSubject);


    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);

    }

    public String generateToken(Map<String, Object> Extraclaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(Extraclaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +2_000*64*24))
                .signWith(getSignIngKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);

    }

    private Claims extractClaims(String jwtToken) {
        return Jwts.parserBuilder().
                setSigningKey(getSignIngKey()).build().
                parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignIngKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token) {
        return etractExpiration(token).before(new Date());
    }

    private Date etractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
