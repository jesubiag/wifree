package services.core.functions;

import operations.requests.PortalNetworkConfigurationRequest;
import operations.responses.PortalNetworkConfigurationResponse;
import services.ServiceType;
import services.WiFreeFunction;

import java.util.function.Function;

public class PortalNetworkConfigurationFunction extends WiFreeFunction<PortalNetworkConfigurationRequest, PortalNetworkConfigurationResponse> {
	
	{
		rqClass = PortalNetworkConfigurationRequest.class;
		rsClass = PortalNetworkConfigurationResponse.class;
		serviceType = ServiceType.TESTING_SERVICE;
	}
	
	@Override
	public Function<PortalNetworkConfigurationRequest, PortalNetworkConfigurationResponse> function() {
		function = request -> new PortalNetworkConfigurationResponse(request.name() + " and Charles");
		return function;
	}
	
}
