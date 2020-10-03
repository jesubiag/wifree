package operations.requests

import operations.core.{WiFreeRequest, RequestType}

case class PortalNetworkConfigurationRequest(name: String) extends WiFreeRequest {
	
	override def requestType: RequestType = RequestType.PORTAL_NETWORK_CONFIGURATION_REQUEST
	
}
