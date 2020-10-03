package services.core;

import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

import static services.core.AnyRange.range;

public class MinutesRange {

    public static final Tuple2<Integer, Integer> RANGE_0_TO_15 = range(0, 14);
    public static final Tuple2<Integer, Integer> RANGE_15_TO_30 = range(15, 29);
    public static final Tuple2<Integer, Integer> RANGE_30_TO_45 = range(30, 44);
    public static final Tuple2<Integer, Integer> RANGE_45_TO_60 = range(45, 59);
    public static final Tuple2<Integer, Integer> RANGE_60_TO_INF = range(60, 10000000);

    public static final List<Tuple2<Integer, Integer>> values = Arrays.asList(
            RANGE_0_TO_15, RANGE_15_TO_30, RANGE_30_TO_45, RANGE_45_TO_60, RANGE_60_TO_INF
    );


}
