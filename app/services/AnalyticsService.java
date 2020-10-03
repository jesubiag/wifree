package services;

import operations.core.WiFreeRequest;
import operations.core.WiFreeResponse;
import operations.requests.*;
import operations.responses.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static services.WiFreeService.process;

public class AnalyticsService extends WiFreeService {
	
	public static CompletionStage<CreateAnalyticsQueryFilterResponse> createAnalyticsQueryFilter(CreateAnalyticsQueryFilterRequest request) {
		return CompletableFuture.supplyAsync(() -> (CreateAnalyticsQueryFilterResponse) process(request));
	}

	public static CompletionStage<GetAnalyticsHomeDatasetsResponse> getAnalyticsHomeDatasets(GetAnalyticsHomeDatasetsRequest request) {
		return CompletableFuture.supplyAsync(() -> (GetAnalyticsHomeDatasetsResponse) process(request));
	}
	
	public static CompletionStage<RunAnalyticsQueryFilterResponse> runAnalyticsQueryFilter(RunAnalyticsQueryFilterRequest request) {
		return CompletableFuture.supplyAsync(() -> (RunAnalyticsQueryFilterResponse) process(request));
	}

	public GetDashboardDataResponse getDashboardData(GetDashboardDataRequest getDashboardDataRequest) {
		return processRequest(getDashboardDataRequest);
	}

	public GetAnalyticsDataResponse getAnalyticsData(GetAnalyticsDataRequest request) {
		return processRequest(request);
	}

	private <RQ extends WiFreeRequest, RS extends WiFreeResponse> RS processRequest(RQ request) {
		return (RS) process(request);
	}
}
