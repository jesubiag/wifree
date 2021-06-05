package services;

import operations.requests.CreateSurveyRequest;
import operations.requests.GetAllSurveysRequest;
import operations.responses.CreateSurveyResponse;
import operations.responses.GetAllSurveysResponse;

public class SurveysService extends WiFreeService {

    public CreateSurveyResponse createSurvey(CreateSurveyRequest createSurveyRequest) {
        return processRequest(createSurveyRequest);
    }

    public GetAllSurveysResponse getAllSurveys(GetAllSurveysRequest getAllSurveysRequest) {
        return processRequest(getAllSurveysRequest);
    }

}
