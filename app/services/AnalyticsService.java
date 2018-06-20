package services;

import operations.requests.CreateAnalyticsQueryFilterRequest;
import operations.responses.CreateAnalyticsQueryFilterResponse;

public class AnalyticsService extends WiFreeService {
	
	public static CreateAnalyticsQueryFilterResponse process(CreateAnalyticsQueryFilterRequest request) {
		return (CreateAnalyticsQueryFilterResponse) WiFreeService.process(request);
	}
	
}
