package com.ssa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPwdResModel {
    @Expose
    @SerializedName("body")
    private Body body;
    @Expose
    @SerializedName("header")
    private Header header;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public class Body {
        @Expose
        @SerializedName("emailId")
        private String emailId;

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }
    }

    public class Header {
        @Expose
        @SerializedName("responseon")
        private String responseon;
        @Expose
        @SerializedName("guid")
        private String guid;
        @Expose
        @SerializedName("geoLocation")
        private String geoLocation;
        @Expose
        @SerializedName("responsefrom")
        private String responsefrom;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("statusCode")
        private String statusCode;

        public String getResponseon() {
            return responseon;
        }

        public void setResponseon(String responseon) {
            this.responseon = responseon;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getGeoLocation() {
            return geoLocation;
        }

        public void setGeoLocation(String geoLocation) {
            this.geoLocation = geoLocation;
        }

        public String getResponsefrom() {
            return responsefrom;
        }

        public void setResponsefrom(String responsefrom) {
            this.responsefrom = responsefrom;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }
    }

}

