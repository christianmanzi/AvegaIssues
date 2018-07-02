/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package logic;

import config.ErrorCodeConfig;
import entities.Issue;
import facades.IssueFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import models.ErrorModel;
import models.ErrorsListModel;
import models.Filter;
import utilities.DataFactory;
import utilities.Log;
import utilities.ReturnUtil;

/**
 *
 * @author aubain
 */
@Stateless
public class IssueProcessor {
    private List<ErrorModel> errors;
    @EJB
            IssueFacade issueFacade;
    public Response create(String body){
        errors = new ArrayList<>();
        Issue issue = new Issue();
        try {
            issue = issue.serialize(body);
            issue.validateOb(issue);
        } catch (IOException | IllegalArgumentException e) {
            return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, e.getMessage());
        }
        //verify external ressource createdBy
        try {
            if(!issueFacade.isExistingProfile(issue.getCreatedBy())){
                ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], "The profile is missing or you are not allowed to perform this action");
                errors.add(errorModel);
                return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
            }
        } catch (IOException e) {
            return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, e.getMessage());
        }
        try {
            issue = issue.prepare(issue);
            issueFacade.create(issue);
            return ReturnUtil.isSuccess(issue.deSerialize(issue));
        } catch(IllegalArgumentException e){
            return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, e.getMessage());
        }catch (Exception e) {
            Log.d(getClass(), e);
            ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], e.getMessage());
            errors.add(errorModel);
            return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
        }
    }
    public Response edit(String body){
        errors = new ArrayList<>();
        Issue issue = new Issue();
        try {
            issue = issue.serialize(body);
            issue.validateOb(issue);
        } catch (IOException | IllegalArgumentException e) {
            return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, e.getMessage());
        }
        //verify external ressource createdBy
        try {
            if(!issueFacade.isExistingProfile(issue.getCreatedBy())){
                ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], "The profile is missing or you are not allowed to perform this action");
                errors.add(errorModel);
                return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
            }
        } catch (IOException e) {
            return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, e.getMessage());
        }
        try {
            issueFacade.edit(issue);
            return ReturnUtil.isSuccess(issue.deSerialize(issue));
        } catch (Exception e) {
            Log.d(getClass(), e);
            ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], e.getMessage());
            errors.add(errorModel);
            return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
        }
    }
    public Response delete(String body){
        ErrorsListModel errorsList=null;
        
        List<Object> objectsList;
        
        try{
            objectsList= DataFactory.stringToObjectList(String.class, body);
        }catch(Exception ex){
            ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.VALIDATION_ERROR[0], ErrorCodeConfig.VALIDATION_ERROR[1],"Invalid Body content, Array/List of Strings [Ids to be deleted] is expected");
            if(errorsList==null)
                errorsList=new ErrorsListModel();
            errorsList.addError(errorModel);
            try{
                return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, errorsList.deSerialize(errorsList));
            }catch(IOException e){
                return ReturnUtil.isFailed(Response.Status.EXPECTATION_FAILED, "Serialization to normal errors list format failure");
            }
        }
        
        try{
            List<String> idsList=new ArrayList();
            
            for(Object o:objectsList)
                idsList.add((String)o);
            
            
            //Confirm all ids are valid id in the role given
            List<Issue> list2delete=new ArrayList();
            Issue issue =null;
            String invalidIdsMessage="Following Ids are invalid :";
            Integer InvalidCount=0;
            for(String s:idsList){
                issue= issueFacade.find(s);
                list2delete.add(issue);
                if(issue==null){
                    invalidIdsMessage+=" - "+s;
                    InvalidCount++;
                }
                
            }
            
            if(InvalidCount>0){
                ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.VALIDATION_ERROR[0], ErrorCodeConfig.VALIDATION_ERROR[1],invalidIdsMessage);
                if(errorsList==null)
                    errorsList=new ErrorsListModel();
                errorsList.addError(errorModel);
                try{
                    return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, errorsList.deSerialize(errorsList));
                }catch(IOException e){
                    return ReturnUtil.isFailed(Response.Status.EXPECTATION_FAILED, "Serialization to normal errors list format failure");
                }
            }            
            
            //set the status of each issue in the list to deleted
            for(Issue i:list2delete){
                if(i.getStatus()==null)
                    i.setStatus(1);
                else
                    i.setStatus(i.getStatus()|1);
                
            }
            
            return ReturnUtil.isSuccess("Success");
            
            
        } catch (Exception e) {
            return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, e.getMessage());
        }
        
    }
    public Response filter(String body){
        errors = new ArrayList<>();
        Filter filter = new Filter();
        try {
            filter = filter.serialize(body);
        } catch (IOException e) {
            return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, e.getMessage());
        }
        try{
            return ReturnUtil.isSuccess(filter.deSerialize(issueFacade.filter(filter)));
        } catch (Exception e) {
            Log.d(IssueProcessor.class, e);
            ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], e.getMessage());
            errors.add(errorModel);
            return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
        }
    }
}

