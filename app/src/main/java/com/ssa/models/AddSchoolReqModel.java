package com.ssa.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddSchoolReqModel {

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
        @SerializedName("modifiedOn")
        private String modifiedOn;
        @Expose
        @SerializedName("modifiedBy")
        private String modifiedBy;
        @Expose
        @SerializedName("createdBy")
        private String createdBy;
        @Expose
        @SerializedName("parentUserRef")
        private String parentUserRef;
        @Expose
        @SerializedName("SchoolUniqueId")
        private String SchoolUniqueId;
        @Expose
        @SerializedName("SchoolName")
        private String SchoolName;
        @Expose
        @SerializedName("createdOn")
        private String createdOn;
        @Expose
        @SerializedName("ParentSchoolId")
        private String ParentSchoolId;
        @Expose
        @SerializedName("userRef")
        private String userRef;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("state")
        private String state;
        @Expose
        @SerializedName("FirstName")
        private String FirstName;
        @Expose
        @SerializedName("LastName")
        private String LastName;
        @Expose
        @SerializedName("Address")
        private String Address;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("EmailId")
        private String EmailId;
        @Expose
        @SerializedName("zip")
        private String zip;

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getParentUserRef() {
            return parentUserRef;
        }

        public void setParentUserRef(String parentUserRef) {
            this.parentUserRef = parentUserRef;
        }

        public String getSchoolUniqueId() {
            return SchoolUniqueId;
        }

        public void setSchoolUniqueId(String schoolUniqueId) {
            SchoolUniqueId = schoolUniqueId;
        }

        public String getSchoolName() {
            return SchoolName;
        }

        public void setSchoolName(String schoolName) {
            SchoolName = schoolName;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getParentSchoolId() {
            return ParentSchoolId;
        }

        public void setParentSchoolId(String parentSchoolId) {
            ParentSchoolId = parentSchoolId;
        }

        public String getUserRef() {
            return userRef;
        }

        public void setUserRef(String userRef) {
            this.userRef = userRef;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEmailId() {
            return EmailId;
        }

        public void setEmailId(String emailId) {
            EmailId = emailId;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }
    }

    public class Header {

        @Expose
        @SerializedName("requestedfrom")
        private String requestedfrom;
        @Expose
        @SerializedName("guid")
        private String guid;
        @Expose
        @SerializedName("userRef")
        private String userRef;
        @Expose
        @SerializedName("parentUserRef")
        private String parentUserRef;
        @Expose
        @SerializedName("requestedon")
        private String requestedon;

        public String getRequestedfrom() {
            return requestedfrom;
        }

        public void setRequestedfrom(String requestedfrom) {
            this.requestedfrom = requestedfrom;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getUserRef() {
            return userRef;
        }

        public void setUserRef(String userRef) {
            this.userRef = userRef;
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
