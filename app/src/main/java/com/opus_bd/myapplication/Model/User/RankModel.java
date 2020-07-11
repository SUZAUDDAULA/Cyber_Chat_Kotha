
package com.opus_bd.myapplication.Model.User;

import android.app.Instrumentation;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RankModel {

    @SerializedName("vehicleType")
    @Expose
    private String vehicleType;

    @SerializedName("Id")
    @Expose
    private Integer id;


    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RankModel{" +
                "vehicleType='" + vehicleType + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}
