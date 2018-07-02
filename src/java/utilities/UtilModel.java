/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author aubain
 */
public interface UtilModel {
    void validateOb(Object object) throws IllegalArgumentException;
    Object prepare(Object object) throws IllegalArgumentException;
}
