package operations.responses

import java.lang.Long
import java.util.{Map => JMap}

import models.types.Gender
import operations.core.{ResponseType, WiFreeResponse}

case class GetDashboardDataResponse(usersConnectedLastWeek: Long,
                                    usersByAgeLastWeek: JMap[(Integer, Integer), Long],
                                    newUsersLastWeek: Long,
                                    usersByGenderLastWeek: JMap[Gender, Long],
                                    busiestDayLastWeek: String,
                                    busiestTimeLastWeek: String,
                                    usersLoyalty: Double,
                                    usersOnline: Long) extends WiFreeResponse {

  override val success: Boolean = true
  override val errors: List[String] = Nil

  override def responseType: ResponseType = ResponseType.GET_DASHBOARD_DATA_RESPONSE

  override def isOk(): Boolean = true

}
