package com.chillarcards.campuswallet.networkmodels.home;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("schoolMachineType")
    @Expose
    private String schoolMachineType;
    @SerializedName("student_data")
    @Expose
    private StudentData studentData;
    @SerializedName("current_balance_exist")
    @Expose
    private String currentBalanceExist;
    @SerializedName("balance_awaiting_to_download")
    @Expose
    private String balanceAwaitingToDownload;
    @SerializedName("school_logo")
    @Expose
    private Object schoolLogo;
    @SerializedName("modules")
    @Expose
    private Modules modules;
    @SerializedName("current_balance")
    @Expose
    private String currentBalance;
    @SerializedName("app_version")
    @Expose
    private String app_version;
    @SerializedName("active_newsletters")
    @Expose
    private List<ActiveNewsletter> activeNewsletters = null;
    @SerializedName("onesignal_keys")
    @Expose
    private List<List<OnesignalKey_>> onesignalKeys = null;

    public String getSchoolMachineType() {
        return schoolMachineType;
    }

    public void setSchoolMachineType(String schoolMachineType) {
        this.schoolMachineType = schoolMachineType;
    }

    public StudentData getStudentData() {
        return studentData;
    }

    public void setStudentData(StudentData studentData) {
        this.studentData = studentData;
    }

    public String getCurrentBalanceExist() {
        return currentBalanceExist;
    }

    public void setCurrentBalanceExist(String currentBalanceExist) {
        this.currentBalanceExist = currentBalanceExist;
    }

    public String getBalanceAwaitingToDownload() {
        return balanceAwaitingToDownload;
    }

    public void setBalanceAwaitingToDownload(String balanceAwaitingToDownload) {
        this.balanceAwaitingToDownload = balanceAwaitingToDownload;
    }

    public Object getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(Object schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public Modules getModules() {
        return modules;
    }

    public void setModules(Modules modules) {
        this.modules = modules;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }
    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public List<ActiveNewsletter> getActiveNewsletters() {
        return activeNewsletters;
    }

    public void setActiveNewsletters(List<ActiveNewsletter> activeNewsletters) {
        this.activeNewsletters = activeNewsletters;
    }
    public List<List<OnesignalKey_>> getOnesignalKeys() {
        return onesignalKeys;
    }

    public void setOnesignalKeys(List<List<OnesignalKey_>> onesignalKeys) {
        this.onesignalKeys = onesignalKeys;
    }
}