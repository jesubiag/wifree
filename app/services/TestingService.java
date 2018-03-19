package services;

import operations.requests.PortalNetworkConfigurationRequest;
import operations.responses.PortalNetworkConfigurationResponse;

public class TestingService extends WiFreeService {
	
//	private static TESTING_SERVICE instance;
	
	private TestingService() {
	}
	
	public static synchronized WiFreeService instance() {
		if (instance == null)
			instance = new TestingService();
		return instance;
	}
	
	public void asad() {
		PortalNetworkConfigurationRequest rq = new PortalNetworkConfigurationRequest("John");
		final PortalNetworkConfigurationResponse rs = (PortalNetworkConfigurationResponse) process(rq);
		System.out.println(rs);
	}
	
}
