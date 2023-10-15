package com.goprea.confidential;

import com.goprea.confidential.annotations.Confidential;

public class ConfidentialData {

    @Confidential
    private String confidentialField;

    private String notConfidential;

    private String password;

    public String getConfidentialField() {
        return confidentialField;
    }

    public void setConfidentialField(String confidentialField) {
        this.confidentialField = confidentialField;
    }

    public String getNotConfidential() {
        return notConfidential;
    }

    public void setNotConfidential(String notConfidential) {
        this.notConfidential = notConfidential;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}