package com.larry.spring.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.larry.spring.dto.request.AuthenticationRequest;
import com.larry.spring.dto.response.AuthenticationResponse;
import com.larry.spring.dto.response.IntrospectResponse;
import com.larry.spring.entity.User;
import com.larry.spring.exception.AppException;
import com.larry.spring.exception.ErrorCode;
import com.larry.spring.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByName(request.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!matches) {
            throw new AppException(ErrorCode.NAME_OR_PASSWORD_NOT_MATCH);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
            .accessToken(token)
            .build();
    }

    public IntrospectResponse introspect(String token) {
        boolean isValid = verifyToken(token);

        return IntrospectResponse.builder()
            .valid(isValid)
            .build();
    }


    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(user.getId())
            .issuer("larry")
            .issueTime(new Date())
            .claim("scope", buildScope(user))
            .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
            .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while signing the token");
        } // 32-byte secret key
    }

    private boolean verifyToken(String token){
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY);

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expirationTime.before(new Date())) {
                return false;
            }

            return signedJWT.verify(verifier);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String buildScope(User user){
        StringJoiner joiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(permission -> {
                joiner.add("ROLE_" + permission.getName());
                if (!CollectionUtils.isEmpty(permission.getPermissions())) {
                    permission.getPermissions().forEach(p -> joiner.add(p.getName()));
                }
            });
        }
        return joiner.toString();
    }
}
