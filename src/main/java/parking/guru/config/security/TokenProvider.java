package parking.guru.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import parking.guru.config.security.oauth2.OAuth2Provider;
import parking.guru.models.enums.Role;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration.minutes}")
    private Long jwtExpirationMinutes;

    public String generate(Authentication authentication, boolean useCasting) {
        CustomUserDetails user;
        List<String> roles;
        if (useCasting) {
            user = (CustomUserDetails) authentication.getPrincipal();
            roles = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

        } else {
            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            user = CustomUserDetails.builder()
                    .email(oauthUser.getEmail())
                    .provider(OAuth2Provider.GOOGLE)
                    .build();
            roles = Collections.singletonList(Role.USER.toString());
        }

        byte[] signingKey = jwtSecret.getBytes();

        return Jwts.builder()
                .header().add("typ", TOKEN_TYPE)
                .and()
                .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512)
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(jwtExpirationMinutes).toInstant()))
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .id(UUID.randomUUID().toString())
                .issuer(TOKEN_ISSUER)
                .audience().add(TOKEN_AUDIENCE)
                .and()
                .subject(user.getUsername())
                .claim("role", roles)
                .claim("name", user.getName())
                .compact();
    }

    public Optional<Jws<Claims>> validateTokenAndGetJws(String token) {
        try {
            byte[] signingKey = jwtSecret.getBytes();

            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(signingKey))
                    .build()
                    .parseSignedClaims(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException exception) {
            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            log.error("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }
        return Optional.empty();
    }

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "parking-guru-api";
    public static final String TOKEN_AUDIENCE = "parking-app";
}
