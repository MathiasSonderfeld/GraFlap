package de.HsH.inform.GraFlap.entity;

public class MetaData {
    String testID = "";

    public String getTestID() {
        return testID.equals("")?"graflap":testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }
}
