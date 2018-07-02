/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import utilities.UtilAbstractModel;

/**
 *
 * @author aubain
 */
public class FilterProfile extends UtilAbstractModel<FilterProfile>{
    private String id;
    private String fname;
    private String otherNames;
    private String phoneNum;
    private String email;
    private String location;
    private String createdBy;
    private DateRange creationTime;
    private boolean isAscending;
    private int limit;
    private int startPosition;

    public FilterProfile() {
        super(FilterProfile.class);
    }

    public FilterProfile(String id) {
        super(FilterProfile.class);
        this.id = id;
    }
    public FilterProfile(String id, int limit) {
        super(FilterProfile.class);
        this.id = id;
        this.limit = limit;
    }

    public FilterProfile(String id, String fname, String otherNames, String phoneNum, String email, String location, String createdBy, DateRange creationTime, boolean isAscending, int limit, int startPosition) {
        super(FilterProfile.class);
        this.id = id;
        this.fname = fname;
        this.otherNames = otherNames;
        this.phoneNum = phoneNum;
        this.email = email;
        this.location = location;
        this.createdBy = createdBy;
        this.creationTime = creationTime;
        this.isAscending = isAscending;
        this.limit = limit;
        this.startPosition = startPosition;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DateRange getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateRange creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isIsAscending() {
        return isAscending;
    }

    public void setIsAscending(boolean isAscending) {
        this.isAscending = isAscending;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
