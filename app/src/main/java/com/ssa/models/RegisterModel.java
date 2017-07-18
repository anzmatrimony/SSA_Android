package com.ssa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("header")
    @Expose
    private Header header;


    public class Body {
        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public class Header {
        @SerializedName("geolocation")
        @Expose
        private String geolocation;
        @SerializedName("requestedfrom")
        @Expose
        private String guid;
        @SerializedName("guid")
        @Expose
        private String requestedfrom;
        @SerializedName("requestedon")
        @Expose
        private String requestedon;
        @SerializedName("userRef")
        @Expose
        private String userRef;

        public String getGeolocation() {
            return geolocation;
        }

        public void setGeolocation(String geolocation) {
            this.geolocation = geolocation;
        }

        public String getRequestedfrom() {
            return requestedfrom;
        }

        public void setRequestedfrom(String requestedfrom) {
            this.requestedfrom = requestedfrom;
        }

        public String getRequestedon() {
            return requestedon;
        }

        public void setRequestedon(String requestedon) {
            this.requestedon = requestedon;
        }

        public String getUserRef() {
            return userRef;
        }

        public void setUserRef(String userRef) {
            this.userRef = userRef;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }
    }

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
}
