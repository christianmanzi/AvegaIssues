package entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-07-02T07:40:41")
@StaticMetamodel(Issue.class)
public class Issue_ { 

    public static volatile SingularAttribute<Issue, Date> creationTime;
    public static volatile SingularAttribute<Issue, String> createdBy;
    public static volatile SingularAttribute<Issue, String> name;
    public static volatile SingularAttribute<Issue, String> description;
    public static volatile SingularAttribute<Issue, String> id;
    public static volatile SingularAttribute<Issue, Integer> status;

}