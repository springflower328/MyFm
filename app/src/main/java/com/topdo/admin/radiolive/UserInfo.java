package com.topdo.admin.radiolive;

import java.io.Serializable;

public class UserInfo implements Serializable {


    private String id;

    private String userName;

    private String userMobileNo;

    private String fcmTocken;

    private String dOB;

    private String location;

    private String licensekey;

    private String createDate;

    private String lastOTP;

    private String lastLogin;


    private String examAttend;


    private String examResult;

    private String RequestType;

    public UserInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getFcmTocken() {
        return fcmTocken;
    }

    public void setFcmTocken(String fcmTocken) {
        this.fcmTocken = fcmTocken;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLicensekey() {
        return licensekey;
    }

    public void setLicensekey(String licensekey) {
        this.licensekey = licensekey;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastOTP() {
        return lastOTP;
    }

    public void setLastOTP(String lastOTP) {
        this.lastOTP = lastOTP;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }


    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    public String getExamAttend() {
        return examAttend;
    }

    public void setExamAttend(String examAttend) {
        this.examAttend = examAttend;
    }

    public String getExamResult() {
        return examResult;
    }

    public void setExamResult(String examResult) {
        this.examResult = examResult;
    }
}
