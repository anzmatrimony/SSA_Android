package com.ssa.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddKidReqModel {
    @Expose
    @SerializedName("body")
    private Body body;
    @Expose
    @SerializedName("header")
    private Header header;

    public class Body {
        @Expose
        @SerializedName("classe")
        private String classe;
        @Expose
        @SerializedName("firstName")
        private String firstName;
        @Expose
        @SerializedName("lastName")
        private String lastName;
        @Expose
        @SerializedName("section")
        private String section;
        @Expose
        @SerializedName("SchoolName")
        private String SchoolName;
        @Expose
        @SerializedName("SchoolUniqueId")
        private String SchoolUniqueId;
        @Expose
        @SerializedName("kidstatus")
        private String kidstatus;
        @Expose
        @SerializedName("createdBy")
        private String createdBy;
        @Expose
        @SerializedName("createdOn")
        private String createdOn;
        @Expose
        @SerializedName("parentUserRef")
        private String parentUserRef;

        public String getClasse() {
            return classe;
        }

        public void setClasse(String classe) {
            this.classe = classe;
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

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSchoolName() {
            return SchoolName;
        }

        public void setSchoolName(String schoolName) {
            SchoolName = schoolName;
        }

        public String getSchoolUniqueId() {
            return SchoolUniqueId;
        }

        public void setSchoolUniqueId(String schoolUniqueId) {
            SchoolUniqueId = schoolUniqueId;
        }

        public String getKidstatus() {
            return kidstatus;
        }

        public void setKidstatus(String kidstatus) {
            this.kidstatus = kidstatus;
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

        public String getParentUserRef() {
            return parentUserRef;
        }

        public void setParentUserRef(String parentUserRef) {
            this.parentUserRef = parentUserRef;
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
        @SerializedName("geolocation")
        private String geolocation;

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

        public String getGeolocation() {
            return geolocation;
        }

        public void setGeolocation(String geolocation) {
            this.geolocation = geolocation;
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
