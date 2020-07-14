
package com.opus_bd.myapplication.Model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactConnectModel {

    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("EmpCode")
    @Expose
    private String empCode;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("DesignationName")
    @Expose
    private String designationName;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    @SerializedName("LastContactTime")
    @Expose
    private String lastContactTime;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(String lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

}
