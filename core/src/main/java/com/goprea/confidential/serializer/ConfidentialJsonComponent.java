//package com.goprea.confidential.serializer;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.*;
//import com.goprea.confidential.annotations.Confidential;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.jackson.JsonComponent;
//
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.PBEKeySpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.SecureRandom;
//import java.security.spec.KeySpec;
//import java.util.Base64;
//
//@JsonComponent
//@Slf4j
//public class ConfidentialJsonComponent {
//
//    public static class ConfidentialSerializer extends JsonSerializer<String> {
//
//        private final SecretKeySpec secretKey =new SecretKeySpec(generateSecretKey().getEncoded(), "AES");
//
//        @Override
//        public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//            log.info("Serializing field " + gen.getOutputContext().getCurrentName());
//            try {
//                if(isFieldConfidential(gen)) {
//                    String fieldName = gen.getOutputContext().getCurrentName();
//                    log.debug("Encrypting field " + fieldName);
//
//                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, CipherConfig.mParamSpec);
//                    String encryptedValue = Base64.getEncoder()
//                            .encodeToString(cipher.doFinal(value.getBytes(StandardCharsets.UTF_8)));
//                    gen.writeString(encryptedValue);
//                } else {
//                    gen.writeString(value);
//                }
//            } catch (Exception ex) {
//                throw new IOException(ex);
//            }
//        }
//
////        @Override
////        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
////                throws JsonMappingException {
////            return new com.goprea.confidential.serializer.ConfidentialJsonComponent.ConfidentialSerializer();
////        }
//
//        private boolean isFieldConfidential(JsonGenerator gen) throws NoSuchFieldException {
//            if(CipherConfig.isInConfidentialList(gen.getOutputContext().getCurrentName())) return true;
//            return gen.getCurrentValue().getClass().getDeclaredField(gen.getOutputContext().getCurrentName()).isAnnotationPresent(Confidential.class);
//        }
//
//        public Class<String> handledType() {
//            return String.class;
//        }
//
//        private static SecretKey generateSecretKey() {
//            try {
//                SecureRandom random = new SecureRandom();
//                byte[] salt = new byte[16];
//                random.nextBytes(salt);
//
//                KeySpec spec = new PBEKeySpec("1234567891123456".toCharArray(), salt, 65536, 256); // AES-256
//                SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//                byte[] key = f.generateSecret(spec).getEncoded();
//                return new SecretKeySpec(key, "AES");
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//
//    public static class ConfidentialDeserializer extends JsonDeserializer<String> {
//
//        private final SecretKeySpec secretKey = new SecretKeySpec(ConfidentialSerializer.generateSecretKey().getEncoded(), "AES");
//
//        @Override
//        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//            log.info("Deserializing field " + p.getCurrentName());
//            String encryptedValue = p.getValueAsString();
//
//            try {
//                if(isFieldConfidential(p)) {
//                    log.info("field is conf");
//                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                    cipher.init(Cipher.DECRYPT_MODE, secretKey, CipherConfig.mParamSpec);
//                    String decryptedValue = new String(cipher.doFinal(Base64.getDecoder().decode(encryptedValue)),
//                            StandardCharsets.UTF_8);
//                    return decryptedValue;
//                } else {
//                    return encryptedValue;
//                }
//            } catch (Exception ex) {
//                throw new IOException(ex);
//            }
//        }
//
////        @Override
////        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
////                throws JsonMappingException {
////            return new com.goprea.confidential.serializer.ConfidentialJsonComponent.ConfidentialDeserializer();
////        }
//
//        private boolean isFieldConfidential(JsonParser p) throws NoSuchFieldException, IOException {
//            if(CipherConfig.isInConfidentialList(p.getCurrentName())) return true;
//            return p.getCurrentValue().getClass().getDeclaredField(p.getCurrentName()).isAnnotationPresent(Confidential.class);
//        }
//    }
//}
