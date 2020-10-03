package operations.requests

import java.time.Instant

import operations.core.{RequestType, WiFreeRequest}

case class GetAnalyticsDataRequest(portalId: Long,
                                   now: Instant) extends WiFreeRequest {
  override def requestType: RequestType = RequestType.GET_ANALYTICS_DATA_REQUEST
}
