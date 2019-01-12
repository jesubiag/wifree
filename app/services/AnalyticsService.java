package services;

import operations.requests.CreateAnalyticsQueryFilterRequest;
import operations.requests.GetAnalyticsHomeDatasetsRequest;
import operations.responses.CreateAnalyticsQueryFilterResponse;
import operations.responses.GetAnalyticsHomeDatasetsResponse;

public class AnalyticsService extends WiFreeService {
	
	public static CreateAnalyticsQueryFilterResponse process(CreateAnalyticsQueryFilterRequest request) {
		return (CreateAnalyticsQueryFilterResponse) WiFreeService.process(request);
	}

	public static GetAnalyticsHomeDatasetsResponse getAnalyticsHomeDatasets(GetAnalyticsHomeDatasetsRequest request) {


		return null;
	}
	
}
