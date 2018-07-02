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
public class InitModel extends UtilAbstractModel<InitModel>{
    private String command;
    private String actionObjectId;
    private String uri;
    private String method;
    private String descr;
    private String requestDescr;
    private String responseDescr;
    private String requestHeaders;
    private String registrationTime;

    public InitModel() {
        super(InitModel.class);
    }

    public InitModel(String command, String actionObjectId, String uri, String method, String descr, String requestDescr, String responseDescr, String requestHeaders, String registrationTime) {
        super(InitModel.class);
        this.command = command;
        this.actionObjectId = actionObjectId;
        this.uri = uri;
        this.method = method;
        this.descr = descr;
        this.requestDescr = requestDescr;
        this.responseDescr = responseDescr;
        this.requestHeaders = requestHeaders;
        this.registrationTime = registrationTime;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getActionObjectId() {
        return actionObjectId;
    }

    public void setActionObjectId(String actionObjectId) {
        this.actionObjectId = actionObjectId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getRequestDescr() {
        return requestDescr;
    }

    public void setRequestDescr(String requestDescr) {
        this.requestDescr = requestDescr;
    }

    public String getResponseDescr() {
        return responseDescr;
    }

    public void setResponseDescr(String responseDescr) {
        this.responseDescr = responseDescr;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
    
}
