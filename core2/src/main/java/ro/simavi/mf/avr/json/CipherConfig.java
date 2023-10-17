package ro.simavi.mf.avr.json;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CipherConfig {
    protected static final Set<String> defaultConfidentialFields = new HashSet<>(Arrays.asList("email", "password", "race", "address", "phone", "ssn", "name", "surname", "firstName", "lastName", "middleName", "fullName", "username"));

    public static boolean isInConfidentialList(String fieldName){
        return defaultConfidentialFields.contains(fieldName);
    }
}
