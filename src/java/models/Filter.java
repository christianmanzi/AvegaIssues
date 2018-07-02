/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package models;

import java.lang.reflect.Field;
import java.util.Date;
import utilities.UtilAbstractModel;

/**
 *
 * @author aubain
 */
public class Filter extends UtilAbstractModel<Filter>{
    private String id;
    private String name;
    private String description;
    private String createdBy;
    private DateRange creationTime;
    private boolean isAscending;
    private int limit;
    private int startPosition;
    
    public Filter() {
        super(Filter.class);
    }
    
    public Filter(String serialNumber, String imei, String brand, String createdBy, DateRange creationTime, boolean isAscending, int limit, int startPosition) {
        super(Filter.class);
        this.id = serialNumber;
        this.name = imei;
        this.description = brand;
        this.createdBy = createdBy;
        this.creationTime = creationTime;
        this.isAscending = isAscending;
        this.limit = limit;
        this.startPosition = startPosition;
    }
    public String fields() throws SecurityException{
        StringBuilder mFields = new StringBuilder();
        try {
            mFields.append("{");
            Field[] fields = getClass().getDeclaredFields();
            int mFieldCounter = fields.length;
            for(Field field : fields){
                mFields.append("\"");
                mFields.append(field.getName());
                mFields.append("\"");
                
                mFields.append(":");
                
                if(field.getType() == DateRange.class){
                    mFields.append("{");
                    Field[] dateRangeFields = DateRange.class.getDeclaredFields();
                    int rangeCounter = dateRangeFields.length;
                    for(Field rangeField : dateRangeFields){
                        mFields.append("\"");
                        mFields.append(rangeField.getName());
                        mFields.append("\"");
                        
                        mFields.append(":");
                        
                        mFields.append("\"");
                        mFields.append("Date format: yyy-MM-dd HH:mm:ss");
                        mFields.append("\"");
                        if(rangeCounter != 1){
                            mFields.append(",");
                            rangeCounter = rangeCounter > 1 ? rangeCounter -- : rangeCounter;
                        }
                    }
                    mFields.append("}");
                }else if(field.getType() == Date.class){
                    mFields.append("\"");
                    mFields.append("Date format: yyy-MM-dd HH:mm:ss");
                    mFields.append("\"");
                }else{
                    mFields.append("\"");
                    mFields.append(field.getType().getSimpleName());
                    mFields.append("\"");
                }
                if(mFieldCounter != 1){
                    mFields.append(",");
                    mFieldCounter = mFieldCounter > 1 ? mFieldCounter -- : mFieldCounter;
                }
            }
            mFields.append("}");
        } catch (SecurityException e) {
            throw e;
        }
        return mFields.toString();
    }
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
}
