package edu.bbte.idde.jdim2141.spring.service;

import edu.bbte.idde.jdim2141.spring.constants.UserRole;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutRoleDto;
import edu.bbte.idde.jdim2141.spring.model.dto.out.OutUserDto;
import edu.bbte.idde.jdim2141.spring.model.jwt.JwtClaims;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    @Value("${auth.jwt.issuer}")
    private String issuer;

    @Value("${auth.jwt.expiration}")
    private Long expirationInMillis;


    public Jwt provideToken(OutUserDto user) {
        Instant now = Instant.now();
        Instant expirationInstant = now.plus(Duration.ofMillis(expirationInMillis));

        JwtClaims jwtClaims = convertUserDataToJwtClaims(user);

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet
            .builder()
            .id(String.valueOf(UUID.randomUUID()))
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(expirationInstant)
            .subject(user.getId().toString())
            .claims(
                claims -> {
                    claims.put("name", jwtClaims.getName());
                    claims.put("email", jwtClaims.getEmail());
                    claims.put("roles", jwtClaims.getRoles());
                }
            )
            .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet));
    }

    private static JwtClaims convertUserDataToJwtClaims(OutUserDto user) {
        JwtClaims jwtClaims = new JwtClaims();
        jwtClaims.setEmail(user.getEmail());
        jwtClaims.setName(user.getName());
        jwtClaims.setRoles(
            user.getRoles().stream().map(OutRoleDto::getName).map(UserRole::name).toList());

        return jwtClaims;
    }
}
