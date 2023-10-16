//package com.goprea.confidential.serializer;
//
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.BeanProperty;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.ContextualSerializer;
//import com.goprea.confidential.annotations.Confidential;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//@Slf4j
//public class ConfidentialSerializer extends JsonSerializer<String> implements ContextualSerializer {
//
//    private final SecretKeySpec secretKey;
//
//    public ConfidentialSerializer(SecretKeySpec secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    @Override
//    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        log.info("Serializing field " + gen.getOutputContext().getCurrentName());
//        try {
//            if(isFieldConfidential(gen)) {
//                String fieldName = gen.getOutputContext().getCurrentName();
//                log.debug("Encrypting field " + fieldName);
//
//                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                cipher.init(Cipher.ENCRYPT_MODE, secretKey, CipherConfig.mParamSpec);
//                String encryptedValue = Base64.getEncoder()
//                        .encodeToString(cipher.doFinal(value.getBytes(StandardCharsets.UTF_8)));
//                gen.writeString(encryptedValue);
//            } else {
//                gen.writeString(value);
//            }
//        } catch (Exception ex) {
//            throw new IOException(ex);
//        }
//    }
//
//    @Override
//    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
//            throws JsonMappingException {
//        return new ConfidentialSerializer(new SecretKeySpec("1234567891123456".getBytes(), "AES"));
//    }
//
//    private boolean isFieldConfidential(JsonGenerator gen) throws NoSuchFieldException {
//        if(CipherConfig.isInConfidentialList(gen.getOutputContext().getCurrentName())) return true;
//        return gen.getCurrentValue().getClass().getDeclaredField(gen.getOutputContext().getCurrentName()).isAnnotationPresent(Confidential.class);
//    }
//
//    public Class<String> handledType() {
//        return String.class;
//    }
//}