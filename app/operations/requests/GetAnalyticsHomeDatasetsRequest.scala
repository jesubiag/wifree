package operations.requests

import operations.core.{RequestType, WiFreeRequest}

case class GetAnalyticsHomeDatasetsRequest(portalId: Long) extends WiFreeRequest {

	override def requestType: RequestType = RequestType.GET_ANALYTICS_HOME_DATASETS_REQUEST

}
