package ro.simavi.mf.avr.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@Slf4j
public class SecretKeyHolder {

    @Value("${confidential.key}")
    @Getter
    private final String secretKey;

    public SecretKeyHolder(@Value("${confidential.key}") String secretKey) {
        this.secretKey = secretKey;
    }


//    private String generateSecretKey() throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
//
//        SecureRandom secureRandom = new SecureRandom();
//        int keyBitSize = 256;
//        keyGenerator.init(keyBitSize, secureRandom);
//
//        String generatedKey = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
//        log.info("Generated key: " + generatedKey);
//        return generatedKey;
//    }

}