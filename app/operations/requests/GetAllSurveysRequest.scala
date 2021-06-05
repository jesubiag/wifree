package operations.requests

import operations.core.RequestType.GET_ALL_SURVEYS_REQUEST
import operations.core.{RequestType, WiFreeRequest}

case class GetAllSurveysRequest(portalId: Long) extends WiFreeRequest {

  override def requestType: RequestType = GET_ALL_SURVEYS_REQUEST

}
