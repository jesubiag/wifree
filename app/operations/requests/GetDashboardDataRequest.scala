package operations.requests

import java.time.Instant

import operations.core.{RequestType, WiFreeRequest}

case class GetDashboardDataRequest(portalId: Long, now: Instant) extends WiFreeRequest {
  override def requestType: RequestType = RequestType.GET_DASHBOARD_DATA_REQUEST
}
