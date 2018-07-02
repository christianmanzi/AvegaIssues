/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package facades;

import entities.Issue;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import models.Filter;
import utilities.Log;

/**
 *
 * @author aubain
 */
@Stateless
public class IssueFacade extends AbstractFacade<Issue> {
    
    @PersistenceContext(unitName = "AvegaIssuesPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public IssueFacade() {
        super(Issue.class);
    }
    public List<Issue> filter(Filter filter)throws Exception{
        try{
            Query q;
            StringBuilder queryString = new StringBuilder("SELECT O FROM Issue O WHERE O.id IS NOT NULL and O.status!=1 and O.status!=3 and O.status!=7 ");
            if(filter == null){
                q = em.createQuery(queryString.toString());
            }else{
                queryString.append(filter.getId() != null ? " AND O.id = :id " : "");
                queryString.append(filter.getName()!= null ? " AND O.name = :name " : "");
                queryString.append(filter.getDescription()!= null ? " AND O.description = :description " : "");
                queryString.append(filter.getCreatedBy()!= null ? " AND O.createdBy = :createdBy " : "");
                queryString.append(filter.getCreationTime()!= null && (filter.getCreationTime().getStartDate() != null && filter.getCreationTime().getEndDate()!= null) ? " AND (O.creationTime >= :startDate AND O.creationTime <= :endDate) " : "");
                
                if(filter.isIsAscending())
                    queryString.append(" ORDER BY O.creationTime ASC ");
                else
                    queryString.append(" ORDER BY O.creationTime DESC ");
                
                q = em.createQuery(queryString.toString());
                
                if(filter.getId() != null){
                    q.setParameter("id", filter.getId());
                }
                if(filter.getName() != null){
                    q.setParameter("name", filter.getName());
                }
                if(filter.getCreationTime() != null && (filter.getCreationTime().getStartDate() != null && filter.getCreationTime().getEndDate()!= null)){
                    q.setParameter("startDate", filter.getCreationTime().getStartDate());
                    q.setParameter("endDate", filter.getCreationTime().getEndDate());
                }
                if(filter.getDescription() != null){
                    q.setParameter("description", filter.getDescription());
                }
                if(filter.getCreatedBy()!= null){
                    q.setParameter("createdBy", filter.getCreatedBy());
                }
                if(filter.getLimit() > 0)
                    q.setMaxResults(filter.getLimit());
                if(filter.getStartPosition() > 0)
                    q.setFirstResult(filter.getStartPosition());
            }
            return q.getResultList();
        }catch(Exception ex){
            Log.d(getClass(), ex);
            throw ex;
        }
    }
}
