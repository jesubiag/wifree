package services.core;

import models.NetworkUserConnectionLog;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface CanGroupLogs {

    default <M, K> Map<K, Long> groupDistinctCounting(List<NetworkUserConnectionLog> logs,
                                                      Function<NetworkUserConnectionLog, M> mapper,
                                                      Function<M, K> keyGrouper) {
        return logs.stream()
                .map(mapper)
                .distinct()
                .collect(Collectors.groupingBy(
                        keyGrouper,
                        Collectors.counting()
                ));
    }

    default <M, K> Map<K, Long> groupCounting(List<NetworkUserConnectionLog> logs,
                                              Function<NetworkUserConnectionLog, M> mapper,
                                              Function<M, K> keyGrouper) {
        return logs.stream()
                .map(mapper)
                .collect(Collectors.groupingBy(
                        keyGrouper,
                        Collectors.counting()
                ));
    }

}
