//package com.goprea.confidential.serializer;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.BeanProperty;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
//import com.goprea.confidential.annotations.Confidential;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.spec.AlgorithmParameterSpec;
//import java.util.Base64;
//
//@Slf4j
//public class ConfidentialDeserializer extends JsonDeserializer<String> implements ContextualDeserializer {
//
//    private final SecretKeySpec secretKey;
//
//    public ConfidentialDeserializer(SecretKeySpec secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    @Override
//    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        log.info("Deserializing field " + p.getCurrentName());
//        String encryptedValue = p.getValueAsString();
//
//        try {
//            if(isFieldConfidential(p)) {
//                log.info("field is conf");
//                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                cipher.init(Cipher.DECRYPT_MODE, secretKey, CipherConfig.mParamSpec);
//                String decryptedValue = new String(cipher.doFinal(Base64.getDecoder().decode(encryptedValue)),
//                        StandardCharsets.UTF_8);
//                return decryptedValue;
//            } else {
//                return encryptedValue;
//            }
//        } catch (Exception ex) {
//            throw new IOException(ex);
//        }
//    }
//
//    @Override
//    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
//            throws JsonMappingException {
//        return new ConfidentialDeserializer(new SecretKeySpec("1234567891123456".getBytes(), "AES"));
//    }
//
//    private boolean isFieldConfidential(JsonParser p) throws NoSuchFieldException, IOException {
//        if(CipherConfig.isInConfidentialList(p.getCurrentName())) return true;
//        return p.getCurrentValue().getClass().getDeclaredField(p.getCurrentName()).isAnnotationPresent(Confidential.class);
//    }
//}