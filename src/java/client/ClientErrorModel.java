/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.ws.rs.core.Response;

/**
 *
 * @author ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr /
 * aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662
 */
public class ClientErrorModel {
    private int statusCode;
    private String received;
    private Response response;
    private String description;

    public ClientErrorModel() {
    }

    public ClientErrorModel(int statusCode, String received, Response response, String description) {
        this.statusCode = statusCode;
        this.received = received;
        this.response = response;
        this.description = description;
    }
    

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
