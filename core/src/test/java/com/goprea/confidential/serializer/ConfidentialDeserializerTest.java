//package com.goprea.confidential.serializer;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.io.ContentReference;
//import com.fasterxml.jackson.core.io.IOContext;
//import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
//import com.fasterxml.jackson.core.util.BufferRecycler;
//import com.fasterxml.jackson.core.util.JsonParserDelegate;
//import com.fasterxml.jackson.databind.BeanProperty;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
//import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
//import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
//import com.fasterxml.jackson.databind.node.MissingNode;
//import com.fasterxml.jackson.databind.node.TreeTraversingParser;
//import com.fasterxml.jackson.databind.util.AccessPattern;
//
//import java.io.ByteArrayInputStream;
//
//import java.io.IOException;
//
//import java.io.UnsupportedEncodingException;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.junit.jupiter.api.Disabled;
//
//import org.junit.jupiter.api.Test;
//
//class ConfidentialDeserializerTest {
//
//    /**
//     * Method under test: {@link ConfidentialDeserializer#ConfidentialDeserializer(SecretKeySpec)}
//     */
//    @Test
//    void testConstructor() throws UnsupportedEncodingException {
//        ConfidentialDeserializer actualConfidentialDeserializer = new ConfidentialDeserializer(
//                new SecretKeySpec("AXAXAXAX".getBytes("UTF-8"), "foo"));
//        assertNull(actualConfidentialDeserializer.getDelegatee());
//        assertFalse(actualConfidentialDeserializer.isCachable());
//        assertNull(actualConfidentialDeserializer.getObjectIdReader());
//        assertNull(actualConfidentialDeserializer.getNullValue());
//        assertEquals(AccessPattern.CONSTANT, actualConfidentialDeserializer.getNullAccessPattern());
//        assertNull(actualConfidentialDeserializer.getKnownPropertyNames());
//        assertNull(actualConfidentialDeserializer.getEmptyValue());
//        assertEquals(AccessPattern.DYNAMIC, actualConfidentialDeserializer.getEmptyAccessPattern());
//    }
//
//    /**
//     * Method under test: {@link ConfidentialDeserializer#createContextual(DeserializationContext, BeanProperty)}
//     */
//    @Test
//    void testCreateContextual() throws JsonMappingException, UnsupportedEncodingException {
//        ConfidentialDeserializer confidentialDeserializer = new ConfidentialDeserializer(
//                new SecretKeySpec("AXAXAXAX".getBytes("UTF-8"), "foo"));
//        DefaultDeserializationContext.Impl ctxt = new DefaultDeserializationContext.Impl(
//                new BeanDeserializerFactory(new DeserializerFactoryConfig()));
//        assertTrue(confidentialDeserializer.createContextual(ctxt,
//                new BeanProperty.Bogus()) instanceof ConfidentialDeserializer);
//    }
//}
//
