package controllers.admin;

import controllers.WiFreeController;
import models.Survey;
import operations.requests.CreateSurveyRequest;
import operations.responses.CreateSurveyResponse;
import play.mvc.Result;
import services.SurveysService;

import javax.inject.Inject;

public class SurveysController extends WiFreeController {

    @Inject
    SurveysService surveysService;

    public Result createSurvey() {
        Survey survey = formFactory.form(Survey.class).bindFromRequest().get();
        CreateSurveyResponse createSurveyResponse = surveysService.createSurvey(new CreateSurveyRequest(survey, portalId()));

        return ok(createSurveyResponse.createdSurvey().toLogString());
    }

}
