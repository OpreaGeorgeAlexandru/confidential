package ro.simavi.mf.avr.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
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
public class ConfidentialSerializer extends JsonSerializer<String> {

    private final SecretKeyHolder secretKeyGenerator;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        log.info("Serializing field");
        if(gen != null && gen.getOutputContext() != null && gen.getOutputContext().getCurrentName() != null) {
            log.info("Serializing field " + gen.getOutputContext().getCurrentName());
        }
        try {
            if(isFieldConfidential(gen)) {
                String fieldName = gen.getOutputContext().getCurrentName();
                log.debug("Encrypting field " + fieldName);

                String encryptedValue = EncryptionDecryptionUtil.decrypt(secretKeyGenerator.getSecretKey(), value);
                gen.writeString(encryptedValue);
            } else {
                gen.writeString(value);
            }
        } catch (Exception ex) {
            log.error("Error serializing field", ex);
            throw new IOException(ex);
        }
    }

    private boolean isFieldConfidential(JsonGenerator gen) {
        if(gen == null || gen.getOutputContext() == null || gen.getOutputContext().getCurrentName() == null) return false;
        if(CipherConfig.isInConfidentialList(gen.getOutputContext().getCurrentName())) return true;

        boolean annotationPresent= false;
        try {
            annotationPresent = gen.getCurrentValue().getClass().getDeclaredField(gen.getOutputContext().getCurrentName()).isAnnotationPresent(Confidential.class);
        } catch (Exception e){
            return false;
        }
        return annotationPresent;
    }

}