package operations.responses

import models.Survey
import operations.core.ResponseType.GET_ALL_SURVEYS_RESPONSE
import operations.core.{ResponseType, WiFreeResponse}
import java.util.{List => JList}

case class GetAllSurveysResponse(surveys: JList[Survey], success: Boolean, errors: List[String]) extends WiFreeResponse {

  override def responseType: ResponseType = GET_ALL_SURVEYS_RESPONSE

  override def isOk(): Boolean = true

}
