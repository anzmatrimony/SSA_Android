package com.ssa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("header")
    @Expose
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

    public class Header {
        @SerializedName("geoLocation")
        @Expose
        private String geoLocation;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("requestedFrom")
        @Expose
        private String requestedFrom;
        @SerializedName("requestedOn")
        @Expose
        private String requestedOn;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("userRef")
        @Expose
        private String userRef;

        public String getGeoLocation() {
            return geoLocation;
        }

        public void setGeoLocation(String geoLocation) {
            this.geoLocation = geoLocation;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getRequestedFrom() {
            return requestedFrom;
        }

        public void setRequestedFrom(String requestedFrom) {
            this.requestedFrom = requestedFrom;
        }

        public String getRequestedOn() {
            return requestedOn;
        }

        public void setRequestedOn(String requestedOn) {
            this.requestedOn = requestedOn;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserRef() {
            return userRef;
        }

        public void setUserRef(String userRef) {
            this.userRef = userRef;
        }


    }

    public class Body {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("PhoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("Role")
        @Expose
        private String role;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("createdOn")
        @Expose
        private String createdOn;
        @SerializedName("emailId")
        @Expose
        private String emailId;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("modifiedBy")
        @Expose
        private String modifiedBy;
        @SerializedName("modifiedOn")
        @Expose
        private String modifiedOn;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("userRef")
        @Expose
        private String userRef;
        @SerializedName("userId")
        @Expose
        private String userId;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserRef() {
            return userRef;
        }

        public void setUserRef(String userRef) {
            this.userRef = userRef;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
