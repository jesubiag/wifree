package services.core.functions;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import daos.NetworkUserConnectionLogDAO;
import models.NetworkUserConnectionLog;
import operations.requests.GetAnalyticsDataRequest;
import operations.responses.DatasetFilter;
import operations.responses.GetAnalyticsDataResponse;
import operations.responses.VisitsByPeriod;
import services.core.CanGroupLogs;
import services.core.ServiceType;
import services.core.WiFreeFunction;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetAnalyticsDataFunction
        extends WiFreeFunction<GetAnalyticsDataRequest, GetAnalyticsDataResponse>
        implements CanGroupLogs {

    @Override
    public Function<GetAnalyticsDataRequest, GetAnalyticsDataResponse> function() {
        function = request -> {
            long portalId = request.portalId();
            Instant now = request.now();
            NetworkUserConnectionLogDAO dao = new NetworkUserConnectionLogDAO();
            DatasetFilter usersConnectedLastYearFilter = DatasetFilter.usersConnectedLastYearFilter(portalId);

            List<NetworkUserConnectionLog> usersConnectedLastYearLogs = dao.findForFilter(usersConnectedLastYearFilter, now);

            List<VisitsByPeriod> visitsByMonth = visitsByMonth(usersConnectedLastYearLogs);

            return new GetAnalyticsDataResponse(visitsByMonth);
        };
        return function;
    }

    private List<VisitsByPeriod> visitsByMonth(List<NetworkUserConnectionLog> usersConnectedLastYearLogs) {
//        Map<String, Long> map = new HashMap<>();
//
//        for (NetworkUserConnectionLog log : usersConnectedLastYearLogs) {
//            String key = log.getConnectionStartDate().toString().split("T")[0].substring(0, 7);
//            Long value = map.getOrDefault(key, 0L);
//            map.put(key, value + 1);
//        }
//
//        return map.entrySet()
//                .stream()
//                .sorted()
//                .map(entry -> new VisitsByPeriod(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());

//        ImmutableListMultimap<String, NetworkUserConnectionLog> multimap = Multimaps.index(
//                usersConnectedLastYearLogs,
//                x -> x.getConnectionStartDate().toString().split("T")[0].substring(0, 7)
//        );
//
//        List<VisitsByPeriod> visitsByPeriod = multimap.asMap()
//                .entrySet()
//                .stream()
//                .map(entry -> new VisitsByPeriod(entry.getKey(), entry.getValue().size()))
//                .collect(Collectors.toList());
//
//        return visitsByPeriod;

        Map<String, Long> visitsByMonthMap = groupCounting(
                usersConnectedLastYearLogs,
                NetworkUserConnectionLog::getConnectionStartDate,
                x -> x.toString().split("T")[0].substring(0, 7)
        );
        ArrayList<String> months = new ArrayList<>(visitsByMonthMap.keySet());
        months.sort(Comparator.naturalOrder());
        return months.stream()
                .map(month -> new VisitsByPeriod(month, visitsByMonthMap.get(month)))
                .collect(Collectors.toList());
    }

    @Override
    public Class<GetAnalyticsDataRequest> rqClass() {
        return GetAnalyticsDataRequest.class;
    }

    @Override
    public Class<GetAnalyticsDataResponse> rsClass() {
        return GetAnalyticsDataResponse.class;
    }

    @Override
    public ServiceType serviceType() {
        return ServiceType.TESTING_SERVICE;
    }

}
