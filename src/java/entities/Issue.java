/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import config.ErrorCodeConfig;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import models.ErrorModel;
import models.ErrorsListModel;
import utilities.DataFactory;
import utilities.IdGen;
import utilities.UtilAbstractModel;
import utilities.UtilModel;

/**
 *
 * @author aubain
 */
@Entity
@Table(name = "issues",
        indexes = {@Index(name = "idx_1", columnList = "id"),
        @Index(name = "idx_2", columnList = "name"),
        @Index(name = "idx_3", columnList = "recordedBy"),
        @Index(name = "idx_4", columnList = "creationTime"),
        @Index(name = "idx_5", columnList = "description")})
public class Issue extends UtilAbstractModel<Issue> implements Serializable, UtilModel {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", length = 90)
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "name", length = 60, unique = true)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "description", length = 90)
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "createdBy", length = 90)
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationTime")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+0")
    private Date creationTime;
    
    @Column(name = "status")
    private Integer status;

    public Issue() {
        super(Issue.class);
    }

    public Issue(String id, String name, String description, String createdBy, Date creationTime,Integer status) {
        super(Issue.class);
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.creationTime = creationTime;
        this.status=status;
    }
        
    @Override
    public void validateOb(Object object) throws IllegalArgumentException {
        List<ErrorModel> errors = new ArrayList<>();
        ErrorModel errorModel;
        if (object == null) {
            errorModel = new ErrorModel(ErrorCodeConfig.PARSING_ERROR[0], ErrorCodeConfig.PARSING_ERROR[1], "Device object is null");
            errors.add(errorModel);
        }
        if(object != null && getClass() != object.getClass()){
            errorModel = new ErrorModel(ErrorCodeConfig.VALIDATION_ERROR[0], ErrorCodeConfig.VALIDATION_ERROR[1], "Could not cast supplied object to Device");
            errors.add(errorModel);
        }
        
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(DataFactory.errorObject(new ErrorsListModel(errors)));
        }
        
        Issue issue = (Issue) object;
        
        if (issue != null && issue.getDescription()== null) {
            errorModel = new ErrorModel(ErrorCodeConfig.VALIDATION_ERROR[0], ErrorCodeConfig.VALIDATION_ERROR[1], Issue.class.getSimpleName()+"/Field: description shouldn't be null");
            errors.add(errorModel);
        }
        if (issue != null && issue.getName()== null) {
            errorModel = new ErrorModel(ErrorCodeConfig.VALIDATION_ERROR[0], ErrorCodeConfig.VALIDATION_ERROR[1], Issue.class.getSimpleName()+"/Field: name shouldn't be null");
            errors.add(errorModel);
        }
        if (issue != null && issue.getCreatedBy()== null) {
            errorModel = new ErrorModel(ErrorCodeConfig.VALIDATION_ERROR[0], ErrorCodeConfig.VALIDATION_ERROR[1], Issue.class.getSimpleName()+"/Field: createdBy shouldn't be null");
            errors.add(errorModel);
        }
        
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(DataFactory.errorObject(new ErrorsListModel(errors)));
        }
    }

    @Override
    public Issue prepare(Object object) throws IllegalArgumentException { 
        List<ErrorModel> errors = new ArrayList<>();
        ErrorModel errorModel;
        if(object == null){
            errorModel = new ErrorModel(ErrorCodeConfig.VALIDATION_ERROR[0], ErrorCodeConfig.VALIDATION_ERROR[1], "Issue object is null");
            errors.add(errorModel);
        }
        if(object != null && getClass() != object.getClass()){
            errorModel = new ErrorModel(ErrorCodeConfig.VALIDATION_ERROR[0], ErrorCodeConfig.VALIDATION_ERROR[1], "Could not cast supplied object to Issue");
            errors.add(errorModel);
        }
        
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(DataFactory.errorObject(new ErrorsListModel(errors)));
        }
        
        Date date = new Date();
        Issue issue = (Issue) object;
        if(issue.getCreationTime() == null)
            issue.setCreationTime(date);
        if(issue.getId() == null)
            issue.setId(IdGen.SIMPLE());
        
        return issue;
    }
    public String fields() throws SecurityException, IOException{
        try {
            Issue issue = new Issue("id", "name", "description", "createdBy", new Date(),0);
            return DataFactory.objectToString(issue);
        } catch (SecurityException | IOException e) {
            throw e;
        }
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
}
