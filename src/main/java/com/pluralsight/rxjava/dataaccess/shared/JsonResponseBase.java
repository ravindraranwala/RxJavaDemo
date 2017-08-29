package com.pluralsight.rxjava.dataaccess.shared;

public class JsonResponseBase {
    
    private String resultStatus;

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}
