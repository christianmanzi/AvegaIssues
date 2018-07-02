/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;
import utilities.UtilAbstractModel;

/**
 *
 * @author ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr /
 * aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662
 */
public class ErrorsListModel extends UtilAbstractModel<ErrorsListModel>{
    private List<ErrorModel> errors;

    public ErrorsListModel() {
        super(ErrorsListModel.class);
    }

    public ErrorsListModel(List<ErrorModel> errors) {
        super(ErrorsListModel.class);
        this.errors = errors;
    }

    public List<ErrorModel> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorModel> errors) {
        this.errors = errors;
    }
     public void addError(ErrorModel error){
        if(errors==null)
            errors=new ArrayList();
        
        this.errors.add(error);
    }
      public void addErrors(List<ErrorModel> errorsList){
        if(errors==null)
            errors=new ArrayList();
        
        this.errors.addAll(errorsList);
    }
    
}
