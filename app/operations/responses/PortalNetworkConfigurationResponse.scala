package operations.responses

import operations.core.{ResponseType, WiFreeResponse}

case class PortalNetworkConfigurationResponse(name: String) extends WiFreeResponse {
	
	override def responseType: ResponseType = ResponseType.PORTAL_NETWORK_CONFIGURATION_RESPONSE
	
	override val success: Boolean = false
	override val errors: List[String] = Nil
	
	override def isOk(): Boolean = true
}
