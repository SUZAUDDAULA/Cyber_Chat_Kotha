package com.opus_bd.myapplication.Model.Group;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupPost {

    @SerializedName("Id")
    @Expose
    private int Id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("employeeId")
    @Expose
    private int employeeId;
    @SerializedName("ids")
    @Expose
    private List<Integer> ids;
    @SerializedName("tagline")
    @Expose
    private String tagline;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}
