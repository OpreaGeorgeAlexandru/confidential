package com.goprea.confidential;

import com.goprea.confidential.annotations.Confidential;
import lombok.Data;

@Data
public class ConfidentialData {

    @Confidential
    private String confidentialField;

    private String notConfidential;

    private String password;
}