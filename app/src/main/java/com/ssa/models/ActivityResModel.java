package com.ssa.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ActivityResModel {
    @Expose
    @SerializedName("body")
    private ArrayList<KidData> arrKids;
    @Expose
    @SerializedName("header")
    private Header header;

    public ArrayList<KidData> getArrKids() {
        return arrKids;
    }

    public void setArrKids(ArrayList<KidData> arrKids) {
        this.arrKids = arrKids;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public class KidData {
        @Expose
        @SerializedName("kiduserID")
        private long kiduserID;
        @Expose
        @SerializedName("kidName")
        private String kidName;
        @Expose
        @SerializedName("data")
        private ArrayList<ArrayList<ActivityData>> arrActivities;

        public long getKiduserID() {
            return kiduserID;
        }

        public void setKiduserID(long kiduserID) {
            this.kiduserID = kiduserID;
        }

        public String getKidName() {
            return kidName;
        }

        public void setKidName(String kidName) {
            this.kidName = kidName;
        }

        public ArrayList<ArrayList<ActivityData>> getArrActivities() {
            return arrActivities;
        }

        public void setArrActivities(ArrayList<ArrayList<ActivityData>> arrActivities) {
            this.arrActivities = arrActivities;
        }
    }

    public class Header {
        @Expose
        @SerializedName("userType")
        private String userType;
        @Expose
        @SerializedName("requestedon")
        private String requestedon;
        @Expose
        @SerializedName("userRef")
        private String userRef;
        @Expose
        @SerializedName("requestedfrom")
        private String requestedfrom;
        @Expose
        @SerializedName("geolocation")
        private String geolocation;

    }

    public class ActivityData {
        @Expose
        @SerializedName("activityID")
        private Integer activityID;
        @Expose
        @SerializedName("teacheruserref")
        private String teacheruserref;
        @Expose
        @SerializedName("activitysubject")
        private String activitysubject;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("kiduserID")
        private String kiduserID;
        @Expose
        @SerializedName("templatename")
        private String templatename;
        @Expose
        @SerializedName("templateID")
        private String templateID;
        @Expose
        @SerializedName("userId")
        private String userId;
        @Expose
        @SerializedName("rowid")
        private Integer rowid;
        @Expose
        @SerializedName("statusupdateon")
        private String statusupdateon;
        @Expose
        @SerializedName("SchoolUniqueId")
        private String SchoolUniqueId;
        @Expose
        @SerializedName("KidName")
        private String KidName;

        public Integer getActivityID() {
            return activityID;
        }

        public void setActivityID(Integer activityID) {
            this.activityID = activityID;
        }

        public String getTeacheruserref() {
            return teacheruserref;
        }

        public void setTeacheruserref(String teacheruserref) {
            this.teacheruserref = teacheruserref;
        }

        public String getActivitysubject() {
            return activitysubject;
        }

        public void setActivitysubject(String activitysubject) {
            this.activitysubject = activitysubject;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getKiduserID() {
            return kiduserID;
        }

        public void setKiduserID(String kiduserID) {
            this.kiduserID = kiduserID;
        }

        public String getTemplatename() {
            return templatename;
        }

        public void setTemplatename(String templatename) {
            this.templatename = templatename;
        }

        public String getTemplateID() {
            return templateID;
        }

        public void setTemplateID(String templateID) {
            this.templateID = templateID;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Integer getRowid() {
            return rowid;
        }

        public void setRowid(Integer rowid) {
            this.rowid = rowid;
        }

        public String getStatusupdateon() {
            return statusupdateon;
        }

        public void setStatusupdateon(String statusupdateon) {
            this.statusupdateon = statusupdateon;
        }

        public String getSchoolUniqueId() {
            return SchoolUniqueId;
        }

        public void setSchoolUniqueId(String schoolUniqueId) {
            SchoolUniqueId = schoolUniqueId;
        }

        public String getKidName() {
            return KidName;
        }

        public void setKidName(String kidName) {
            KidName = kidName;
        }
    }
}
