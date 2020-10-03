package services.core;

import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

import static services.core.AnyRange.range;

public class HourRange {

    public static final Tuple2<Integer, Integer> RANGE_0_TO_8 = range(0, 7);
    public static final Tuple2<Integer, Integer> RANGE_8_TO_11 = range(8, 10);
    public static final Tuple2<Integer, Integer> RANGE_11_TO_13 = range(11, 12);
    public static final Tuple2<Integer, Integer> RANGE_13_TO_16 = range(13, 15);
    public static final Tuple2<Integer, Integer> RANGE_16_TO_20 = range(16, 19);
    public static final Tuple2<Integer, Integer> RANGE_20_TO_24 = range(20, 23);

    public static final List<Tuple2<Integer, Integer>> values = Arrays.asList(
            RANGE_0_TO_8, RANGE_8_TO_11, RANGE_11_TO_13, RANGE_13_TO_16, RANGE_16_TO_20, RANGE_20_TO_24
    );

}
