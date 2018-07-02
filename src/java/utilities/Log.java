/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import static java.lang.System.out;

/**
 *
 * @author aubain
 */
public class Log {
   public static final void d(Class className, Throwable e){
        e.printStackTrace(out);
    }
    public static final void d(Class className, String message){
        if(message != null && className != null)
            out.print(className.toString()+message);
    }
}
