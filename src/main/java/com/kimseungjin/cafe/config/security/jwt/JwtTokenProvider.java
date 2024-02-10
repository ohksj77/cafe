package com.kimseungjin.cafe.config.security.jwt;

import com.kimseungjin.cafe.config.security.exception.InvalidTokenException;
import com.kimseungjin.cafe.config.security.user.UserAuthDetailsService;
import com.kimseungjin.cafe.config.security.user.UserAuthorityManager;
import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.domain.member.entity.Role;

import io.jsonwebtoken.*;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final Long ACCESS_TOKEN_EXPIRE_LENGTH = 60L * 60 * 1000; // 1 hour
    private static final Long REFRESH_TOKEN_EXPIRE_LENGTH = 60L * 60 * 24 * 1000; // 24 hour
    private static final String JWT_SUBJECT = "jwt-auth";
    private static final String AUTHORITIES_KEY = "auth";
    private final UserAuthorityManager userAuthorityManager;
    private final UserAuthDetailsService userAuthDetailsService;
    private final Key key;

    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (ExpiredJwtException | UnsupportedJwtException | IllegalStateException e) {
            throw new InvalidTokenException();
        }
    }

    public JwtToken createToken(final Member user) {
        final String accessTokenValue = createAccessTokenValue(user);
        final String refreshTokenValue = createRefreshTokenValue();

        return JwtToken.builder()
                .accessToken(accessTokenValue)
                .refreshToken(refreshTokenValue)
                .build();
    }

    private String createAccessTokenValue(final Member user) {
        final long now = new Date().getTime();
        final Claims claims = createAccessTokenClaims(user);

        return Jwts.builder()
                .setSubject(JWT_SUBJECT)
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_LENGTH))
                .compact();
    }

    private Claims createRefreshTokenClaims() {
        return Jwts.claims();
    }

    private String createRefreshTokenValue() {
        final long now = new Date().getTime();
        final Claims claims = createRefreshTokenClaims();

        return Jwts.builder()
                .setSubject(JWT_SUBJECT)
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_LENGTH))
                .compact();
    }

    private Claims createAccessTokenClaims(final Member user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getId().toString());
        claims.put(AUTHORITIES_KEY, user.getRole().name());
        return claims;
    }

    public Authentication getAuthentication(final String token) {
        final Claims claims = parseClaims(token);

        final List<SimpleGrantedAuthority> authorities = mapToAuthorities(claims);
        final UserDetails userDetails =
                userAuthDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), authorities);
    }

    private List<SimpleGrantedAuthority> mapToAuthorities(final Claims claims) {
        return Optional.ofNullable(claims.get(AUTHORITIES_KEY))
                .map(role -> userAuthorityManager.mapToAuthorities(Role.valueOf(role.toString())))
                .orElseThrow(InvalidTokenException::new);
    }

    private Claims parseClaims(final String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException();
        }
    }
}
