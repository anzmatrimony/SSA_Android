package com.ssa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SchoolModel {

    @SerializedName("body")
    @Expose
    private ArrayList<Schools> arrSchools;
    @SerializedName("header")
    @Expose
    private Header header;

    public ArrayList<Schools> getArrSchools() {
        return arrSchools;
    }

    public void setArrSchools(ArrayList<Schools> arrSchools) {
        this.arrSchools = arrSchools;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public class Schools {

        @SerializedName("ParentSchoolId")
        @Expose
        private String parentSchoolId;
        @SerializedName("SchoolName")
        @Expose
        private String schoolName;
        @SerializedName("SchoolUniqueId")
        @Expose
        private String schoolUniqueId;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("createdOn")
        @Expose
        private String createdOn;
        @SerializedName("modifiedBy")
        @Expose
        private String modifiedBy;
        @SerializedName("modifiedOn")
        @Expose
        private String modifiedOn;
        @SerializedName("parentUserRef")
        @Expose
        private String parentUserRef;

        public String getParentSchoolId() {
            return parentSchoolId;
        }

        public void setParentSchoolId(String parentSchoolId) {
            this.parentSchoolId = parentSchoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getSchoolUniqueId() {
            return schoolUniqueId;
        }

        public void setSchoolUniqueId(String schoolUniqueId) {
            this.schoolUniqueId = schoolUniqueId;
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

        public String getParentUserRef() {
            return parentUserRef;
        }

        public void setParentUserRef(String parentUserRef) {
            this.parentUserRef = parentUserRef;
        }

        @Override
        public String toString() {
            return schoolName;
        }
    }

    public class Header {
        @SerializedName("geolocation")
        @Expose
        private String geolocation;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("parentUserRef")
        @Expose
        private String parentUserRef;
        @SerializedName("requestedfrom")
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
