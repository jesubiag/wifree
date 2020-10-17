package operations.requests

import models.Survey
import operations.core.RequestType.CREATE_SURVEY_REQUEST
import operations.core.{RequestType, WiFreeRequest}

case class CreateSurveyRequest(survey: Survey) extends WiFreeRequest {

  override def requestType: RequestType = CREATE_SURVEY_REQUEST


}
