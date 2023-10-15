package com.goprea.confidential.serializer;

import javax.crypto.spec.IvParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CipherConfig {
    public static final AlgorithmParameterSpec mParamSpec = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });

    public static final Set<String> defaultConfidentialFields = new HashSet<>(Arrays.asList("email", "password", "secret"));

    public static boolean isInConfidentialList(String fieldName){
        return defaultConfidentialFields.contains(fieldName);
    }
}
