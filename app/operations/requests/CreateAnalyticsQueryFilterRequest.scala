package operations.requests

import models.AnalyticsQueryFilter
import operations.core.{RequestType, WiFreeRequest}

case class CreateAnalyticsQueryFilterRequest(queryFilter: AnalyticsQueryFilter) extends WiFreeRequest {
	
	override def requestType: RequestType = RequestType.CREATE_ANALYTICS_QUERY_FILTER_REQUEST
	
}
