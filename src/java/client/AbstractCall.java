/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package client;

import config.ErrorCodeConfig;
import java.io.IOException;
import static java.lang.System.out;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import models.ApiResponse;
import models.ErrorModel;
import models.ErrorsListModel;
import utilities.DataFactory;
import utilities.Log;

/**
 *
 * @author ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr /
 * aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662
 */
public class AbstractCall {
    private Object responseClass;
    
    public AbstractCall(Object responseClass) {
        this.responseClass = responseClass;
    }
    
    public ApiResponse rawPost(String url, MultivaluedMap<String, Object> headers, String body, String mediaType) throws Exception{
        
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], ErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], e.getMessage()));
            ErrorsListModel errors = new ErrorsListModel(error);
            Log.d(getClass(), e);
            throw new URISyntaxException(url, errors.deSerialize(errors));
        }
        
        Client client;
        WebTarget target;
        Response response;
        try {
            Log.d(getClass(), "External call request URL: "+url+" | body: "+body);
            Log.d(getClass(), "External call request header: "+headers.toString());
            
            client = ClientBuilder.newClient();
            target =client.target(url);
            response = target.request()
                    .headers(headers)
                    .post(Entity.entity(body, mediaType));
        } catch (Exception e) {
            Log.d(getClass(), e);
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.NORMAL_ERROR[0], ErrorCodeConfig.NORMAL_ERROR[1], e.getMessage()));
            ErrorsListModel errors = new ErrorsListModel(error);
            throw new Exception(errors.deSerialize(errors));
        }
        
        if(response == null){
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.NORMAL_ERROR[0], ErrorCodeConfig.NORMAL_ERROR[1], "Couldn't been able to get results."));
            ErrorsListModel errors = new ErrorsListModel(error);
            throw new Exception(errors.deSerialize(errors));
        }
        
        List<ErrorModel> eErrors = new ArrayList<>();
        int statusCode;
        String received = null;
        try {
            statusCode = response.getStatus();
            received = response.readEntity(String.class).trim();
            Log.d(getClass(), "External call response Status code:"+statusCode+" body: "+received);
        } catch (NullPointerException e) {
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.NORMAL_ERROR[0], ErrorCodeConfig.NORMAL_ERROR[1], e.getMessage()));
            ErrorsListModel errors = new ErrorsListModel(error);
            throw new Exception(errors.deSerialize(errors));
        }
        if(statusCode != 200){
            try {
                ErrorsListModel errorsListModel = new ErrorsListModel().serialize(received);
                errorsListModel.getErrors().forEach((errorModel) -> {
                    eErrors.add(errorModel);
                });
                eErrors.add(new ErrorModel(ErrorCodeConfig.EXTERNAL_API_ERROR[0], ErrorCodeConfig.EXTERNAL_API_ERROR[1], uri.getPath()+" External API responded with error code:"+statusCode+" body: "+received));
            } catch (IOException e) {
               ErrorsListModel errorsListModel = new ErrorsListModel().serialize(e.getMessage());
               for(ErrorModel errorModel : errorsListModel.getErrors()){
                   eErrors.add(errorModel);
               }
               eErrors.add(new ErrorModel(ErrorCodeConfig.EXTERNAL_API_ERROR[0], ErrorCodeConfig.EXTERNAL_API_ERROR[1], " External API responded with error code:"+statusCode+" body: "+received));
            } catch(NullPointerException e){
                eErrors.add(new ErrorModel(ErrorCodeConfig.EXTERNAL_API_ERROR[0], ErrorCodeConfig.EXTERNAL_API_ERROR[1], uri.getPath()+" External API responded with error code:"+statusCode+" body: "+received+" Processing results error: "+e.getMessage()));
            }
            throw new IOException(new ErrorsListModel().deSerialize(new ErrorsListModel(eErrors)));
        }
        
        return new ApiResponse(statusCode, received);
    }
    public ApiResponse rawGet(String url, MultivaluedMap<String, Object> headers, String mediaType)throws Exception{
        try {
            URI uri = new URI(url);
            out.print("External call request url: "+url);
            out.print("External call request header: "+headers.toString());
            Client client = ClientBuilder.newClient();
            WebTarget target =client.target(url);
            Response response;
            if(headers == null){
                response = target.request().get();
            }else{
                response = target.request()
                        .headers(headers)
                        .get();
            }
            List<ErrorModel> errors = new ArrayList<>();
            int statusCode = response.getStatus();
            String received = response.readEntity(String.class).trim();
            out.print("External call response Status code:"+statusCode+" body: "+received);
            if(statusCode != 200){
                try {
                    List<Object> objects = DataFactory.stringToObjectList(ErrorModel.class, received);
                    if(!objects.isEmpty()){
                        for(Object o : objects){
                            errors.add((ErrorModel) o);
                        }
                        errors.add(new ErrorModel(ErrorCodeConfig.EXTERNAL_API_ERROR[0], ErrorCodeConfig.EXTERNAL_API_ERROR[1], uri.getPath()+" External API responded with error"));
                    }
                } catch (Exception e) {
                    e.printStackTrace(out);
                }
                if(errors.isEmpty()){
                    throw new Exception(received);
                }else{
                    return new ApiResponse(statusCode, DataFactory.objectToString(errors));
                }
            }
            return new ApiResponse(statusCode, received);
        } catch (Exception e) {
            List<ErrorModel> error = Arrays.asList(new ErrorModel(ErrorCodeConfig.NORMAL_ERROR[0], ErrorCodeConfig.NORMAL_ERROR[1], e.getMessage()));
            throw new Exception(DataFactory.errorObject(new ErrorsListModel(error)));
        }
    }
}
