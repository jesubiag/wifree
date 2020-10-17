package services;

import operations.requests.CreateSurveyRequest;
import operations.responses.CreateSurveyResponse;

public class SurveysService extends WiFreeService {

    public CreateSurveyResponse createSurvey(CreateSurveyRequest createSurveyRequest) {
        return processRequest(createSurveyRequest);
    }

}
