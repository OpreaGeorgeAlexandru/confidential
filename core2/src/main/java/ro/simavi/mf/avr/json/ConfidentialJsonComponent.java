package ro.simavi.mf.avr.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import ro.simavi.mf.avr.annotations.Confidential;
import ro.simavi.mf.avr.utils.EncryptionDecryptionUtil;
import ro.simavi.mf.avr.utils.SecretKeyGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@JsonComponent
@Slf4j
public class ConfidentialJsonComponent {

    static String secretKey;

    static {
        try {
            secretKey = SecretKeyGenerator.getSecretKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ConfidentialSerializer extends JsonSerializer<String> {

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

                    String encryptedValue = EncryptionDecryptionUtil.decrypt(secretKey, value);
                    gen.writeString(encryptedValue);
                } else {
                    gen.writeString(value);
                }
            } catch (Exception ex) {
                log.error("Error serializing field", ex);
                throw new IOException(ex);
            }
        }

        private boolean isFieldConfidential(JsonGenerator gen) throws NoSuchFieldException {
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

    public static class ConfidentialDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            log.info("Deserializing field");
            if(p != null && p.getCurrentName() != null) {
                log.info("Deserializing field " + p.getCurrentName());
            }
            String encryptedValue = p.getValueAsString();

            try {
                if(isFieldConfidential(p)) {
                    log.info("field is conf " + encryptedValue);
                    return EncryptionDecryptionUtil.encrypt(secretKey, encryptedValue);
                } else {
                    return encryptedValue;
                }
            } catch (Exception ex) {
                log.error("Error deserializing field", ex);
                throw new IOException(ex);
            }
        }

        private boolean isFieldConfidential(JsonParser p) throws NoSuchFieldException, IOException {
            if(p == null || p.getCurrentName() == null) return false;
            if(CipherConfig.isInConfidentialList(p.getCurrentName())) return true;
//            return false;
            return p.getCurrentValue().getClass().getDeclaredField(p.getCurrentName()).isAnnotationPresent(Confidential.class);
        }
    }


}
