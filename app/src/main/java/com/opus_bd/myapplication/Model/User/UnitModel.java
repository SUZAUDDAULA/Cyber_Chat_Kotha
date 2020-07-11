package com.opus_bd.myapplication.Model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitModel {

    @SerializedName("Id")
    @Expose
    private Integer Id;
    @SerializedName("branchUnitName")
    @Expose
    private String branchUnitName;
    @SerializedName("branchUnitNameBN")
    @Expose
    private String branchUnitNameBN;
    @SerializedName("branchCode")
    @Expose
    private String branchCode;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getBranchUnitName() {
        return branchUnitName;
    }

    public void setBranchUnitName(String branchUnitName) {
        this.branchUnitName = branchUnitName;
    }

    public String getBranchUnitNameBN() {
        return branchUnitNameBN;
    }

    public void setBranchUnitNameBN(String branchUnitNameBN) {
        this.branchUnitNameBN = branchUnitNameBN;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

}
