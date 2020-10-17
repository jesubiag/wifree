package services;

import services.core.Functions;
import operations.core.WiFreeRequest;
import operations.core.WiFreeResponse;
import services.core.WiFreeFunction;

public abstract class WiFreeService {
	
	protected static WiFreeService instance;
	
	//<T> boolean isOk(T anything);
	
	public static WiFreeResponse process(WiFreeRequest request) {
		final WiFreeFunction function = getFunction(request);
		return function.apply(request);
	}

	protected <RQ extends WiFreeRequest, RS extends WiFreeResponse> RS processRequest(RQ request) {
		return (RS) process(request);
	}
	
	private static <T extends WiFreeRequest> WiFreeFunction getFunction(T request) {
		return Functions.of(request);
	}

}
