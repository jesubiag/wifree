package operations.responses

import models.AnalyticsQueryFilter
import operations.core.{ResponseType, WiFreeResponse}

case class CreateAnalyticsQueryFilterResponse(createdQueryFilter: AnalyticsQueryFilter, success: Boolean, errors: List[String]) extends WiFreeResponse {
	
	override def responseType: ResponseType = ResponseType.CREATE_ANALYTICS_QUERY_FILTER_RESPONSE
	
	override def isOk(): Boolean = true		// TODO implement
	
}
