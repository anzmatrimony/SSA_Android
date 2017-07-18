package com.ssa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPwdModel {

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
        @SerializedName("requestedFrom")
        private String requestedFrom;
        @Expose
        @SerializedName("guid")
        private String guid;
        @Expose
        @SerializedName("geoLocation")
        private String geoLocation;
        @Expose
        @SerializedName("requestedOn")
        private String requestedOn;

        public String getRequestedFrom() {
            return requestedFrom;
        }

        public void setRequestedFrom(String requestedFrom) {
            this.requestedFrom = requestedFrom;
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

        public String getRequestedOn() {
            return requestedOn;
        }

        public void setRequestedOn(String requestedOn) {
            this.requestedOn = requestedOn;
        }
    }
}
