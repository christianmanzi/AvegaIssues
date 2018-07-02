/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package client;

import config.ErrorCodeConfig;
import config.UrlConfig;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import models.ApiResponse;
import models.ErrorModel;
import models.ErrorsListModel;
import models.FilterProfile;
import models.InitModel;
import models.ProfileModel;

/**
 *
 * @author ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr /
 * aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662
 */
public class ExternalService {
    public static final List<ProfileModel> FILTER_PROFILE(FilterProfile filterProfile)throws Exception{
        //configure extras
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("cmd", UrlConfig.FILTER_PROFILE);
        APIClient apic = new APIClient(ApiResponse.class);
        ApiResponse response;
        String requestBody;
        try {
            requestBody = filterProfile.deSerialize(filterProfile);
        } catch (IOException e) {
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.PARSING_ERROR[0], ErrorCodeConfig.PARSING_ERROR[1], e.getMessage()));
            ErrorsListModel errors = new ErrorsListModel(error);
            throw new Exception(errors.deSerialize(errors));
        }
        try {
            response = apic.rawPost(UrlConfig.PROFILE_URL, headers, requestBody, MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            throw e;
        }
        
        try {
            return new ProfileModel().serializeList(response.getBody().toString());
        } catch (IOException e) {
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.PARSING_ERROR[0], ErrorCodeConfig.PARSING_ERROR[1], e.getMessage()));
            ErrorsListModel errors = new ErrorsListModel(error);
            throw new Exception(errors.deSerialize(errors));
        }
    }
    
    public static final int INIT(InitModel initModel)throws Exception{
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("cmd", UrlConfig.INIT_CMD);
        APIClient apic = new APIClient(ApiResponse.class);
        ApiResponse response;
        String requestBody;
        try {
            requestBody = initModel.deSerialize(initModel);
        } catch (IOException e) {
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.PARSING_ERROR[0], ErrorCodeConfig.PARSING_ERROR[1], e.getMessage()));
            ErrorsListModel errors = new ErrorsListModel(error);
            throw new Exception(errors.deSerialize(errors));
        }
        try {
            response = apic.rawPost(UrlConfig.INIT_URL, headers, requestBody, MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            throw e;
        }
        
        try {
            return response.getStatusCode();
        } catch (NullPointerException e) {
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.PARSING_ERROR[0], ErrorCodeConfig.PARSING_ERROR[1], e.getMessage()));
            ErrorsListModel errors = new ErrorsListModel(error);
            throw new Exception(errors.deSerialize(errors));
        }
    }
    
     public static final int UNREGISTER_CMD(String commandName)throws Exception{
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("cmd", UrlConfig.UNREGISTER_CMD);
        APIClient apic = new APIClient(ApiResponse.class);
        ApiResponse response;
        String requestBody;
      
            requestBody = commandName;
      
        try {
            response = apic.rawPost(UrlConfig.INIT_URL, headers, requestBody, MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            throw e;
        }
        
        try {
            return response.getStatusCode();
        } catch (NullPointerException e) {
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.PARSING_ERROR[0], ErrorCodeConfig.PARSING_ERROR[1], e.getMessage()));
            ErrorsListModel errors = new ErrorsListModel(error);
            throw new Exception(errors.deSerialize(errors));
        }
    }
    
    
}
