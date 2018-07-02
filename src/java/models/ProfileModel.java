/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import utilities.UtilAbstractModel;

/**
 *
 * @author aubain
 */
public class ProfileModel extends UtilAbstractModel<ProfileModel> {
    private String id;
    private String fname;    
    private String otherNames;    
    private String email;    
    private String phoneNum;    
    private String location; // province 1d,district 2d, sector 2d,cell 2,
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+0")
    private Date creationTime;

    public ProfileModel() {
        super(ProfileModel.class);
    }

    public ProfileModel(String id, String fname, String otherNames, String email, String phoneNum, String location, String createdBy, Date creationTime) {
        super(ProfileModel.class);
        this.id = id;
        this.fname = fname;
        this.otherNames = otherNames;
        this.email = email;
        this.phoneNum = phoneNum;
        this.location = location;
        this.createdBy = createdBy;
        this.creationTime = creationTime;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
