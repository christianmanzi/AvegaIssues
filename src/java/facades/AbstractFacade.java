/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import client.ExternalService;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import models.FilterProfile;
import models.ProfileModel;
import utilities.Log;

/**
 *
 * @author aubain
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws Exception{
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if(constraintViolations.size() > 0){
            String err = "Error";
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while(iterator.hasNext()){
                ConstraintViolation<T> cv = iterator.next();
                err = cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() + " " +cv.getMessage();
                Log.d(entityClass, new RuntimeException(cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() + " " +cv.getMessage()));
            }
            throw new Exception(err);
        }else{
            getEntityManager().persist(entity);
            getEntityManager().flush();
        }
    }
    
    public void edit(T entity)throws Exception{
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if(constraintViolations.size() > 0){
            String err = "Error";
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while(iterator.hasNext()){
                ConstraintViolation<T> cv = iterator.next();
                err = cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() + " " +cv.getMessage();
                Log.d(entityClass, new RuntimeException(cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() + " " +cv.getMessage()));
            }
            throw new Exception(err);
        }else{
            getEntityManager().merge(entity);
            getEntityManager().flush();
        }
    }
    
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    public boolean isExistingProfile(String profileId) throws IOException{
        try {
            List<ProfileModel> mProfiles = ExternalService.FILTER_PROFILE(new FilterProfile(profileId, 1));
            return !mProfiles.isEmpty();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
