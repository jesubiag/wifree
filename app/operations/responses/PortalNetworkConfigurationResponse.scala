package operations.responses

import operations.core.{ResponseType, WiFreeResponse}

case class PortalNetworkConfigurationResponse(name: String) extends WiFreeResponse {
	
	override def responseType: ResponseType = ResponseType.PORTAL_NETWORK_CONFIGURATION_RESPONSE
	
}
