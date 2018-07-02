/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import logic.Command;

/**
 * REST Web Service
 *
 * @author ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr /
 * aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662
 */
@Path("/process")
@Stateless
public class Services {
   
    @Context
    private UriInfo context;
    @EJB
            Command command;
    @POST
    @Path("/fact")
    @Produces(MediaType.APPLICATION_JSON)
    public Response process(@Context HttpServletRequest requestContext, @Context HttpHeaders headers, String body) {
        return command.exec(headers, body);
    }
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response action(@Context HttpServletRequest requestContext, @Context HttpHeaders headers, @QueryParam("action") String action) {
        return command.init(action);
    } 
}
