package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.WiFreeController;
import controllers.api.dto.SocialLoginPortalConfigDTO;
import controllers.api.dto.SurveyLoginPortalConfigDTO;
import daos.PortalDAO;
import daos.SurveyDAO;
import models.Portal;
import models.Survey;
import play.libs.Json;
import play.mvc.Result;

import javax.inject.Inject;

import static java.util.Optional.ofNullable;

public class PortalConfigController extends WiFreeController {

    @Inject
    private PortalDAO portalDAO;

    @Inject
    private SurveyDAO surveyDAO;

    public Result getPortalConfig(Long portalId) {
        return ofNullable(portalDAO.get(portalId))
                .map(portal -> ok(getLoginConfigDTO(portalId, portal)))
                .orElse(badRequest("Portal [" + portalId + "] not found."));
    }

    private JsonNode getLoginConfigDTO(Long portalId, Portal portal) {
        if (portal.hasSocialLoginEnabled())
            return SocialLoginPortalConfigDTO.json(portal);
        else {
            Survey portalActiveSurvey = surveyDAO.findPortalActiveSurvey(portalId);
            return SurveyLoginPortalConfigDTO.json(portal, portalActiveSurvey);
        }
    }

}
