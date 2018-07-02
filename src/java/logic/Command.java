/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package logic;

import config.CmdConfig;
import config.ErrorCodeConfig;
import config.HeaderConfig;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import models.ErrorModel;
import models.ErrorsListModel;
import utilities.DataFactory;
import utilities.ReturnUtil;

/**
 *
 * @author aubain
 */
@Stateless
public class Command {
    @EJB
            IssueProcessor processor;
    @EJB
            Initiator initiator;
    private List<ErrorModel> errors;
    public Response exec(HttpHeaders headers, String body){
        try {
            CmdConfig command = CmdConfig.valueOf(headers.getHeaderString(HeaderConfig.CMD).toUpperCase());
            switch(command){
                case CREATE_ISSUE:
                    return processor.create(body);
                 case DELETE_ISSUE:
                    return processor.delete(body);
                case EDIT_ISSUE:
                    return processor.edit(body);
                case FILTER_ISSUE:
                    return processor.filter(body);
                default:
                    errors = new ArrayList<>();
                    ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], "Invalid Command");
                    errors.add(errorModel);
                    return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
                    
            }
        } catch (Exception e) {
            errors = new ArrayList<>();
            e.printStackTrace(out);
            ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], e.getMessage());
            errors.add(errorModel);
            return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
        }
    }
    public Response init(String action){
        try {
            errors = new ArrayList<>();
            if(action.equals("INIT")){
                return initiator.init();
            }else{
                errors = new ArrayList<>();
                ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], "Invalid action");
                errors.add(errorModel);
                return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
            }
        } catch (Exception e) {
            errors = new ArrayList<>();
            e.printStackTrace(out);
            ErrorModel errorModel = new ErrorModel(ErrorCodeConfig.INTERNAL_ERROR[0], ErrorCodeConfig.INTERNAL_ERROR[1], e.getMessage());
            errors.add(errorModel);
            return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, DataFactory.errorObject(new ErrorsListModel(errors)));
        }
    }
}
