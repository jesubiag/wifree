package operations.responses

import operations.core.{ResponseType, WiFreeResponse}

case class GetAnalyticsHomeDatasetsResponse() extends WiFreeResponse {

	override val success: Boolean = ???
	override val errors: List[String] = ???

	override def responseType: ResponseType = ResponseType.GET_ANALYTICS_HOME_DATASETS_RESPONSE

	override def isOk(): Boolean = ???

}
