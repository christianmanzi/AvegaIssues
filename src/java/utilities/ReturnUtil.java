/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.File;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Hp
 */
public class ReturnUtil {
    public static final Response isSuccess( String outPut){
        return Response.ok(outPut, MediaType.APPLICATION_JSON).build();
    }
    public static final Response isFile( File outPut){
        return Response.ok(outPut, MediaType.APPLICATION_OCTET_STREAM).build();
    }
    public static final Response isFailed(Status statusCode, String cause){
        return Response.status(statusCode).entity(""+cause).build();
    }
    public static final Response isRaw(int statusCode, String raw){
        return Response.status(statusCode).entity(""+raw).build();
    }
}
