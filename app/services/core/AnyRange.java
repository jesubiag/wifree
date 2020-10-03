package services.core;

import scala.Tuple2;

import java.util.List;

public interface AnyRange {

    static Tuple2<Integer, Integer> range(Integer min, Integer max) {
        return Tuple2.apply(min, max);
    }

    static Tuple2<Integer, Integer> toRange(List<Tuple2<Integer, Integer>> ranges, Integer value) {
        return ranges.stream()
                .filter(range -> range._1 <= value && value <= range._2)
                .findFirst()
                .get();
    }

}
