package operations.requests

import models.AnalyticsQueryFilter
import operations.core.{RequestType, WiFreeRequest}

case class RunAnalyticsQueryFilterRequest(queryFilter: AnalyticsQueryFilter) extends WiFreeRequest {
	
	override def requestType: RequestType = RequestType.RUN_ANALYTICS_QUERY_FILTER_REQUEST
	
}
