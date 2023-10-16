//package com.goprea.confidential.serializer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.goprea.confidential.ConfidentialData;
//import org.junit.jupiter.api.Test;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.PBEKeySpec;
//import javax.crypto.spec.SecretKeySpec;
//
//import java.security.SecureRandom;
//import java.security.spec.KeySpec;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ConfidentialSerializerTest {
//
//    @Test
//    void testSerializationAndDeserialization() throws JsonProcessingException {
//        // Generate a random secret key for encryption and decryption
//        SecretKey secretKey = generateSecretKey();
//
//        // Create an instance of the confidential data class
//        ConfidentialData data = new ConfidentialData();
//        data.setConfidentialField("confidential value");
//        data.setNotConfidential("not conf");
//        data.setPassword("this is my password");
//        // Create an object mapper with the confidential data serializer and deserializer
//        ObjectMapper mapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(String.class, new ConfidentialSerializer(new SecretKeySpec(secretKey.getEncoded(), "AES")));
//        module.addDeserializer(String.class, new ConfidentialDeserializer(new SecretKeySpec(secretKey.getEncoded(), "AES")));
//        mapper.registerModule(module);
//
//        // Serialize the data to JSON
//        String json = mapper.writeValueAsString(data);
//        System.out.println(json);
//
//        // Deserialize the JSON to a new instance of the confidential data class
//        ConfidentialData deserializedData = mapper.readValue(json, ConfidentialData.class);
//
//        // Check that the confidential field was properly encrypted and decrypted
//        assertEquals(data.getConfidentialField(), deserializedData.getConfidentialField());
//    }
//
//    private SecretKey generateSecretKey() {
//        try {
//            SecureRandom random = new SecureRandom();
//            byte[] salt = new byte[16];
//            random.nextBytes(salt);
//
//            KeySpec spec = new PBEKeySpec("1234567891123456".toCharArray(), salt, 65536, 256); // AES-256
//            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            byte[] key = f.generateSecret(spec).getEncoded();
//            return new SecretKeySpec(key, "AES");
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//}