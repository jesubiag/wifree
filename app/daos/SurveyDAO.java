package daos;

import models.Survey;

import javax.annotation.Nullable;
import java.util.List;

import static io.ebean.Expr.and;
import static io.ebean.Expr.eq;

public class SurveyDAO extends GenericDAO<Survey> {

    public SurveyDAO() {
        super(Survey.class);
    }

    public List<Survey> findByPortal(Long portalId) {
        return listWhere(eq("portal.id", portalId));
    }

    @Nullable
    public Survey findPortalActiveSurvey(Long portalId) {
        return find(and(eq("portal.id", portalId), eq("enabled", true)));
    }

}
