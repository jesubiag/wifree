package operations.responses

import operations.core.{ResponseType, WiFreeResponse}
import java.util.{List => JList}

case class GetAnalyticsDataResponse(visitsByMonth: JList[VisitsByPeriod]) extends WiFreeResponse {

  override val success: Boolean = true
  override val errors: List[String] = Nil

  override def responseType: ResponseType = ResponseType.GET_ANALYTICS_DATA_RESPONSE

  override def isOk(): Boolean = true

}

case class VisitsByPeriod(period: String, visits: Long)