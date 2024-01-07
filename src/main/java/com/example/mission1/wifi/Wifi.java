package com.example.mission1.wifi;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wifi {

    private double distance;
    @SerializedName("X_SWIFI_MGR_NO")
    private String manageNo;
    @SerializedName("X_SWIFI_WRDOFC")
    private String borough;
    @SerializedName("X_SWIFI_MAIN_NM")
    private String wifiName;
    @SerializedName("X_SWIFI_ADRES1")
    private String roadAddr;
    @SerializedName("X_SWIFI_ADRES2")
    private String detailAddr;
    @SerializedName("X_SWIFI_INSTL_FLOOR")
    private String intallLoc;
    @SerializedName("X_SWIFI_INSTL_TY")
    private String installType;
    @SerializedName("X_SWIFI_INSTL_MBY")
    private String installAgency;
    @SerializedName("X_SWIFI_SVC_SE")
    private String serviceClassify;
    @SerializedName("X_SWIFI_CMCWR")
    private String netType;
    @SerializedName("X_SWIFI_CNSTC_YEAR")
    private int installYear;
    @SerializedName("X_SWIFI_INOUT_DOOR")
    private String inOrout;
    @SerializedName("X_SWIFI_REMARS3")
    private String wifiConEnv;
    @SerializedName("LNT")
    private double lat;
    @SerializedName("LAT")
    private double lnt;
    @SerializedName("WORK_DTTM")
    private String workDate;
}
