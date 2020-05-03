package operations.responses

import java.util.{List => JList, Map => JMap}

import operations.core.{ResponseType, WiFreeResponse}
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class GetAnalyticsDataResponse(visitsByMonthLastYear: JList[VisitsByPeriod],
                                    visitsByMonthLastYearByGender: VisitsByPeriodByGender,
                                    visitsByDayByTimeRange: VisitsByDayByTimeRange,
                                    visitsByDurationLastWeek: JMap[(Integer, Integer), JList[VisitsByPeriod]]) extends WiFreeResponse {

  override val success: Boolean = true
  override val errors: List[String] = Nil

  override def responseType: ResponseType = ResponseType.GET_ANALYTICS_DATA_RESPONSE

  override def isOk(): Boolean = true

}

case class VisitsByPeriod(period: String, visits: Long)

object VisitsByPeriod {

  implicit val writes: OWrites[VisitsByPeriod] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "value").write[Long]
    )(unlift(VisitsByPeriod.unapply))

}

case class VisitsByPeriodByGender(male: JList[VisitsByPeriod], female: JList[VisitsByPeriod])

case class VisitsByDayByTimeRange(visits0_8: JList[VisitsByPeriod],
                                  visits8_11: JList[VisitsByPeriod],
                                  visits11_13: JList[VisitsByPeriod],
                                  visits13_16: JList[VisitsByPeriod],
                                  visits16_20: JList[VisitsByPeriod],
                                  visits20_24: JList[VisitsByPeriod])
