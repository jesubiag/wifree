package services.core;

import operations.core.WiFreeRequest;
import operations.core.WiFreeResponse;
import services.core.ServiceType;

import java.util.function.Function;

public abstract class WiFreeFunction<RQ extends WiFreeRequest, RS extends WiFreeResponse> {
	
	protected Class<RQ> rqClass;
	protected Class<RS> rsClass;
	protected Function<RQ, RS> function;
	protected ServiceType serviceType;
	
	public abstract Function<RQ, RS> function();
	
	final public RS apply(RQ request) {
		return function().apply(request);
	}
	
	final public Class<RQ> rqClass() {
		return this.rqClass;
	}
	
	final public Class<RS> rsClass() {
		return this.rsClass;
	}
	
	final public ServiceType serviceType() {
		return this.serviceType;
	}
	
}
