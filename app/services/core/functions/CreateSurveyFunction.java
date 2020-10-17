package services.core.functions;

import daos.SurveyDAO;
import models.Survey;
import operations.requests.CreateSurveyRequest;
import operations.responses.CreateSurveyResponse;
import services.core.ServiceType;
import services.core.WiFreeFunction;

import java.util.function.Function;

@SuppressWarnings("unused")
public class CreateSurveyFunction extends WiFreeFunction<CreateSurveyRequest, CreateSurveyResponse> {

    @Override
    public Function<CreateSurveyRequest, CreateSurveyResponse> function() {
        function = request -> {
            SurveyDAO surveyDAO = new SurveyDAO();
            Survey survey = request.survey();
            surveyDAO.save(survey);
            return new CreateSurveyResponse(survey, true, null);
        };
        return function;
    }

    @Override
    public Class<CreateSurveyRequest> rqClass() {
        return CreateSurveyRequest.class;
    }

    @Override
    public Class<CreateSurveyResponse> rsClass() {
        return CreateSurveyResponse.class;
    }

    @Override
    public ServiceType serviceType() {
        return ServiceType.TESTING_SERVICE;
    }

}
