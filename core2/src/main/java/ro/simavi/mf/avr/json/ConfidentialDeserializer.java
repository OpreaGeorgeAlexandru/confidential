package ro.simavi.mf.avr.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import ro.simavi.mf.avr.annotations.Confidential;
import ro.simavi.mf.avr.utils.EncryptionDecryptionUtil;
import ro.simavi.mf.avr.utils.SecretKeyHolder;

import java.io.IOException;

@Slf4j
@JsonComponent
@AllArgsConstructor
public class ConfidentialDeserializer extends JsonDeserializer<String> {

    private final SecretKeyHolder secretKeyHolder;

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        log.info("Deserializing field");
        if (p != null && p.getCurrentName() != null) {
            log.info("Deserializing field " + p.getCurrentName());
        }
        if (p == null) {
            return null;
        }
        String encryptedValue = p.getValueAsString();

        try {
            if (isFieldConfidential(p)) {
                log.info("field is conf " + encryptedValue);
                return EncryptionDecryptionUtil.encrypt(secretKeyHolder.getSecretKey(), encryptedValue);
            } else {
                return encryptedValue;
            }
        } catch (Exception ex) {
            log.error("Error deserializing field", ex);
            throw new IOException(ex);
        }
    }

    private boolean isFieldConfidential(JsonParser p) throws NoSuchFieldException, IOException {
        if (p == null || p.getCurrentName() == null) return false;
        if (CipherConfig.isInConfidentialList(p.getCurrentName())) return true;
        return p.getCurrentValue().getClass().getDeclaredField(p.getCurrentName()).isAnnotationPresent(Confidential.class);
    }
}