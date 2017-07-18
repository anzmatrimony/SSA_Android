package com.ssa.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolsListModel {

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

    public class Body {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("PhoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("Address")
        @Expose
        private String Address;
        @SerializedName("modifiedBy")
        @Expose
        private String modifiedBy;
        @SerializedName("Status")
        @Expose
        private String Status;
        @SerializedName("RequestID")
        @Expose
        private String RequestID;
        @SerializedName("createdOn")
        @Expose
        private String createdOn;
        @SerializedName("SchoolName")
        @Expose
        private String SchoolName;
        @SerializedName("LastName")
        @Expose
        private String LastName;
        @SerializedName("modifiedOn")
        @Expose
        private String modifiedOn;
        @SerializedName("EmailId")
        @Expose
        private String EmailId;
        @SerializedName("FirstName")
        @Expose
        private String FirstName;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("zip")
        @Expose
        private String zip;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("SchoolUniqueId")
        @Expose
        private String SchoolUniqueId;
        @SerializedName("WebSite")
        @Expose
        private String WebSite;
        @SerializedName("city")
        @Expose
        private String city;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getRequestID() {
            return RequestID;
        }

        public void setRequestID(String requestID) {
            RequestID = requestID;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getSchoolName() {
            return SchoolName;
        }

        public void setSchoolName(String schoolName) {
            SchoolName = schoolName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public String getEmailId() {
            return EmailId;
        }

        public void setEmailId(String emailId) {
            EmailId = emailId;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getSchoolUniqueId() {
            return SchoolUniqueId;
        }

        public void setSchoolUniqueId(String schoolUniqueId) {
            SchoolUniqueId = schoolUniqueId;
        }

        public String getWebSite() {
            return WebSite;
        }

        public void setWebSite(String webSite) {
            WebSite = webSite;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    public class Header {

        @SerializedName("userRef")
        @Expose
        private String userRef;
        @SerializedName("requestedon")
        @Expose
        private String requestedon;
        @SerializedName("requestedfrom")
        @Expose
        private String requestedfrom;
        @SerializedName("SchoolUniqueId")
        @Expose
        private String SchoolUniqueId;
        @SerializedName("geolocation")
        @Expose
        private String geolocation;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("parentUserRef")
        @Expose
        private String parentUserRef;

        public String getUserRef() {
            return userRef;
        }

        public void setUserRef(String userRef) {
            this.userRef = userRef;
        }

        public String getRequestedfrom() {
            return requestedfrom;
        }

        public void setRequestedfrom(String requestedfrom) {
            this.requestedfrom = requestedfrom;
        }

        public String getSchoolUniqueId() {
            return SchoolUniqueId;
        }

        public void setSchoolUniqueId(String schoolUniqueId) {
            SchoolUniqueId = schoolUniqueId;
        }

        public String getGeolocation() {
            return geolocation;
        }

        public void setGeolocation(String geolocation) {
            this.geolocation = geolocation;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getParentUserRef() {
            return parentUserRef;
        }

        public void setParentUserRef(String parentUserRef) {
            this.parentUserRef = parentUserRef;
        }

        public String getRequestedon() {
            return requestedon;
        }

        public void setRequestedon(String requestedon) {
            this.requestedon = requestedon;
        }
    }
}
