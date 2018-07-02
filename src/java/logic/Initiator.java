/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package logic;

import client.ExternalService;
import config.CmdConfig;
import entities.Issue;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import models.Filter;
import models.InitModel;
import utilities.ReturnUtil;

/**
 *
 * @author aubain
 */
@Stateless
public class Initiator {
    public Response init(){
        CmdConfig[]cmds = CmdConfig.values();
        InitModel initModel;
        int system = 0;
        try {
            for(CmdConfig cmd : cmds){
                initModel = new InitModel();
                
                //command
                initModel.setCommand(String.valueOf(cmd));
                initModel.setActionObjectId("0");
                
                //path
                initModel.setUri("http://18.218.142.179:8080/AvegaIssues/issues/process/fact");
                initModel.setMethod("POST");
                
                //description
                initModel.setDescr("Command that executes: "+String.valueOf(cmd));
                
                String data = "";
                if(cmd.equals(CmdConfig.FILTER_ISSUE)){
                    data = new Filter().fields();
                }else{
                    data = new Issue().fields();
                }
                //Request data
                initModel.setRequestDescr(data);
                
                //Response data
                initModel.setResponseDescr(data);
                
                //header
                initModel.setRequestHeaders("cmd:"+String.valueOf(cmd));
                
                
                //remote request: Unregister to be able to register again
                
                if(ExternalService.UNREGISTER_CMD(cmd+"") != 200){
                    system += 1;
                }
                
                if(ExternalService.INIT(initModel) != 200){
                    system += 1;
                }
            }
            
            if(system > 0){
                return ReturnUtil.isFailed(Response.Status.BAD_REQUEST, "Component initialization ended with error: "+system);
            }else{
                return ReturnUtil.isSuccess("Component initiated.");
            }
        } catch (Exception e) {
            return ReturnUtil.isFailed(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
