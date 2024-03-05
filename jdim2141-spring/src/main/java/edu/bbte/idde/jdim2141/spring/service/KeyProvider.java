package edu.bbte.idde.jdim2141.spring.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeyProvider {

    public static final String ALGORITHM = "RSA";

    private KeyPair keyPair;

    public KeyProvider() {
        generateKeyPair();
    }

    public RSAPrivateKey getPrivateKey() {
        return (RSAPrivateKey) keyPair.getPrivate();
    }

    public RSAPublicKey getPublicKey() {
        return (RSAPublicKey) keyPair.getPublic();
    }

    private void generateKeyPair() {
        try {
            keyPair = KeyPairGenerator.getInstance(ALGORITHM).generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            log.debug(e.getMessage());
            /*throw new UnexpectedServiceException(e);*/
        }
    }
}
