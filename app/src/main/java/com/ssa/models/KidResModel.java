package com.ssa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KidResModel {

    @SerializedName("body")
    @Expose
    private Body arrKids;
    @SerializedName("header")
    @Expose
    private Header header;

    public class Body{
        @SerializedName("smessage")
        @Expose
        private String smessage;
    }

    public class Header {
        @SerializedName("geolocation")
        @Expose
        private String geolocation;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("requestedfrom")
        @Expose
        private String requestedfrom;
        @SerializedName("requestedon")
        @Expose
        private String requestedon;
        @SerializedName("userRef")
        @Expose
        private String userRef;
    }
}
