package services;

import operations.requests.CreateAnalyticsQueryFilterRequest;
import operations.requests.GetAnalyticsHomeDatasetsRequest;
import operations.requests.RunAnalyticsQueryFilterRequest;
import operations.responses.CreateAnalyticsQueryFilterResponse;
import operations.responses.GetAnalyticsHomeDatasetsResponse;
import operations.responses.RunAnalyticsQueryFilterResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AnalyticsService extends WiFreeService {
	
	public static CompletionStage<CreateAnalyticsQueryFilterResponse> createAnalyticsQueryFilter(CreateAnalyticsQueryFilterRequest request) {
		return CompletableFuture.supplyAsync(() -> (CreateAnalyticsQueryFilterResponse) WiFreeService.process(request));
	}

	public static CompletionStage<GetAnalyticsHomeDatasetsResponse> getAnalyticsHomeDatasets(GetAnalyticsHomeDatasetsRequest request) {
		return CompletableFuture.supplyAsync(() -> (GetAnalyticsHomeDatasetsResponse) process(request));
	}
	
	public static CompletionStage<RunAnalyticsQueryFilterResponse> runAnalyticsQueryFilter(RunAnalyticsQueryFilterRequest request) {
		return CompletableFuture.supplyAsync(() -> (RunAnalyticsQueryFilterResponse) process(request));
	}
	
}
