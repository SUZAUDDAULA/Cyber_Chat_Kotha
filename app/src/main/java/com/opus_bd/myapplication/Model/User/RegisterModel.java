package com.opus_bd.myapplication.Model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("BPNumber")
    @Expose
    private String BPNumber;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("ConfirmPassword")
    @Expose
    private String ConfirmPassword;
    @SerializedName("unitId")
    @Expose
    private Integer unitId;
    @SerializedName("subUnitId")
    @Expose
    private Integer subUnitId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBPNumber() {
        return BPNumber;
    }
    public void setBPNumber(String BPNumber) {
        this.BPNumber = BPNumber;
    }

    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }
    public void setConfirmPassword(String ConfirmPassword) {
        this.ConfirmPassword = ConfirmPassword;
    }

    public Integer getUnitId() {
        return unitId;
    }
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getSubUnitId() {
        return subUnitId;
    }
    public void setSubUnitId(Integer subUnitId) {
        this.subUnitId = subUnitId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
