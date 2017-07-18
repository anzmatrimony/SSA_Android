package com.ssa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class KidModel {

    @SerializedName("body")
    @Expose
    private ArrayList<Kids> arrKids;
    @SerializedName("header")
    @Expose
    private Header header;

    public ArrayList<Kids> getArrKids() {
        return arrKids;
    }

    public void setArrKids(ArrayList<Kids> arrKids) {
        this.arrKids = arrKids;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public class Kids implements Serializable {

        @SerializedName("KidName")
        @Expose
        private String KidName;
        @SerializedName("Class")
        @Expose
        private String kidClass;
        @SerializedName("Image")
        @Expose
        private String Image;
        @SerializedName("Relationship")
        @Expose
        private String Relationship;
        @SerializedName("SchoolUniqueId")
        @Expose
        private String SchoolUniqueId;
        @SerializedName("Section")
        @Expose
        private String Section;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("createdOn")
        @Expose
        private String createdOn;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("kidId")
        @Expose
        private String kidId;
        @SerializedName("kidStatus")
        @Expose
        private String kidStatus;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("modifiedBy")
        @Expose
        private String modifiedBy;
        @SerializedName("modifiedOn")
        @Expose
        private String modifiedOn;
        @SerializedName("parentUserRef")
        @Expose
        private String parentUserRef;
        @SerializedName("schoolName")
        @Expose
        private String schoolName;

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getKidClass() {
            return kidClass;
        }

        public void setKidClass(String kidClass) {
            this.kidClass = kidClass;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }

        public String getRelationship() {
            return Relationship;
        }

        public void setRelationship(String relationship) {
            Relationship = relationship;
        }

        public String getSchoolUniqueId() {
            return SchoolUniqueId;
        }

        public void setSchoolUniqueId(String schoolUniqueId) {
            SchoolUniqueId = schoolUniqueId;
        }

        public String getSection() {
            return Section;
        }

        public void setSection(String section) {
            Section = section;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getKidId() {
            return kidId;
        }

        public void setKidId(String kidId) {
            this.kidId = kidId;
        }

        public String getKidStatus() {
            return kidStatus;
        }

        public void setKidStatus(String kidStatus) {
            this.kidStatus = kidStatus;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
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

        public String getKidName() {
            return KidName;
        }

        public void setKidName(String kidName) {
            KidName = kidName;
        }
    }

    public class Header {
        @SerializedName("userType")
        @Expose
        private String userType;
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

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
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
