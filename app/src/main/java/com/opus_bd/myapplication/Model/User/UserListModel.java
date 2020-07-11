package com.opus_bd.myapplication.Model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserListModel {
    @SerializedName("aspnetId")
    @Expose
    private String aspnetId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserTypeId")
    @Expose
    private Object userTypeId;
    @SerializedName("companyId")
    @Expose
    private Object companyId;
    @SerializedName("EmpCode")
    @Expose
    private String empCode;
    @SerializedName("FinancialValue")
    @Expose
    private Object financialValue;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("DivisionName")
    @Expose
    private String divisionName;
    @SerializedName("DesignationName")
    @Expose
    private String designationName;
    @SerializedName("userTypeName")
    @Expose
    private Object userTypeName;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("empType")
    @Expose
    private Object empType;
    @SerializedName("joiningDate")
    @Expose
    private Object joiningDate;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("photoId")
    @Expose
    private Integer photoId;
    @SerializedName("projectId")
    @Expose
    private Object projectId;
    @SerializedName("roleId")
    @Expose
    private String roleId;
    @SerializedName("Id")
    @Expose
    private Object id;
    @SerializedName("specialBranchUnitId")
    @Expose
    private Object specialBranchUnitId;

    public String getAspnetId() {
        return aspnetId;
    }

    public void setAspnetId(String aspnetId) {
        this.aspnetId = aspnetId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Object userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Object getFinancialValue() {
        return financialValue;
    }

    public void setFinancialValue(Object financialValue) {
        this.financialValue = financialValue;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public Object getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(Object userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Object getEmpType() {
        return empType;
    }

    public void setEmpType(Object empType) {
        this.empType = empType;
    }

    public Object getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Object joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public Object getProjectId() {
        return projectId;
    }

    public void setProjectId(Object projectId) {
        this.projectId = projectId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getSpecialBranchUnitId() {
        return specialBranchUnitId;
    }

    public void setSpecialBranchUnitId(Object specialBranchUnitId) {
        this.specialBranchUnitId = specialBranchUnitId;
    }
/*
    @SerializedName("aspnetId")
    @Expose
    private String aspnetId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("UserTypeId")
    @Expose
    private Object userTypeId;
    @SerializedName("companyId")
    @Expose
    private Object companyId;
    @SerializedName("EmpCode")
    @Expose
    private String empCode;
    @SerializedName("FinancialValue")
    @Expose
    private Object financialValue;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("DivisionName")
    @Expose
    private String divisionName;
    @SerializedName("DesignationName")
    @Expose
    private String designationName;
    @SerializedName("userTypeName")
    @Expose
    private Object userTypeName;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("departmentName")
    @Expose
    private Object departmentName;
    @SerializedName("empType")
    @Expose
    private Object empType;
    @SerializedName("joiningDate")
    @Expose
    private Object joiningDate;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("photoId")
    @Expose
    private Integer photoId;
    @SerializedName("projectId")
    @Expose
    private Object projectId;
    @SerializedName("roleId")
    @Expose
    private String roleId;
    @SerializedName("Id")
    @Expose
    private Object id;
    @SerializedName("specialBranchUnitId")
    @Expose
    private Object specialBranchUnitId;

    public String getAspnetId() {
        return aspnetId;
    }

    public void setAspnetId(String aspnetId) {
        this.aspnetId = aspnetId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Object userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Object getFinancialValue() {
        return financialValue;
    }

    public void setFinancialValue(Object financialValue) {
        this.financialValue = financialValue;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public Object getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(Object userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Object getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(Object departmentName) {
        this.departmentName = departmentName;
    }

    public Object getEmpType() {
        return empType;
    }

    public void setEmpType(Object empType) {
        this.empType = empType;
    }

    public Object getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Object joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public Object getProjectId() {
        return projectId;
    }

    public void setProjectId(Object projectId) {
        this.projectId = projectId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getSpecialBranchUnitId() {
        return specialBranchUnitId;
    }

    public void setSpecialBranchUnitId(Object specialBranchUnitId) {
        this.specialBranchUnitId = specialBranchUnitId;
    }*/

}