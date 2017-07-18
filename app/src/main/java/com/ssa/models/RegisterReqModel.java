package com.ssa.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterReqModel {

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
        @SerializedName("Gender")
        private String gender;
        @Expose
        @SerializedName("emailId")
        private String emailId;
        @Expose
        @SerializedName("firstName")
        private String firstName;
        @Expose
        @SerializedName("lastName")
        private String lastName;
        @Expose
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("phoneNumber")
        private String phoneNumber;
        @Expose
        @SerializedName("userId")
        private String userId;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public class Header {
        @Expose
        @SerializedName("geolocation")
        private String geolocation;
        @Expose
        @SerializedName("requestedfrom")
        private String requestedfrom;
        @Expose
        @SerializedName("requestedon")
        private String requestedon;
        @Expose
        @SerializedName("userRef")
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
    }
}
