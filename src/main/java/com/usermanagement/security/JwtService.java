package com.usermanagement.security;

import com.usermanagement.dto.JwtTokenRequest;
import com.usermanagement.dto.JwtTokenResponse;
import com.usermanagement.entity.UserEntity;
import com.usermanagement.services.service.UserService;
import com.usermanagement.util.PropertyUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService extends PropertyUtils {

    private final UserService userService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public JwtTokenResponse generateToken(JwtTokenRequest jwtTokenRequest) {
        return generateToken(new HashMap<>(), jwtTokenRequest);
    }

    public JwtTokenResponse generateToken(Map<String, Object> extraClaims, JwtTokenRequest jwtTokenRequest) {
        return buildToken(extraClaims, jwtTokenRequest);
    }

    public long getExpirationTime() {
        return this.getJwtExpiration();
    }

    private void validateUser(JwtTokenRequest jwtTokenRequest) {
        final UserEntity user = this.userService.findUserByUsername(jwtTokenRequest.getUsername()).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        if (!BCrypt.checkpw(jwtTokenRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
    }

    private JwtTokenResponse buildToken(Map<String, Object> extraClaims, JwtTokenRequest jwtTokenRequest) {
        this.validateUser(jwtTokenRequest);
        final String accessToken = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(jwtTokenRequest.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.getJwtExpiration()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        final String refreshToken = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(jwtTokenRequest.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (this.getJwtExpiration() * 2)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        return JwtTokenResponse
                .builder()
                .accessToken(this.wrapPrefix(accessToken))
                .refreshToken(this.wrapPrefix(refreshToken))
                .tokenValid(this.getExpirationTime())
                .build();
    }

    private String wrapPrefix(String jwt) {
        return this.getJwtPrefix().concat(" ").concat(jwt);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
