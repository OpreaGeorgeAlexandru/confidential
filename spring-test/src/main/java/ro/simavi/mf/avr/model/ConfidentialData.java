package ro.simavi.mf.avr.model;

import lombok.Data;
import ro.simavi.mf.avr.annotations.Confidential;

@Data
public class ConfidentialData {

    @Confidential
    private String confidentialField;

    private String notConfidential;

    private String password;
}