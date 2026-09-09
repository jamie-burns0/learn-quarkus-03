package me.jamieburns;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("FDS2")
final class FrequencyDistributionService2 implements FrequencyDistributionService {

    private static final Comparator<Map.Entry<Integer, Integer>> N_COMPARATOR_FN = Map.Entry.comparingByKey();

    private static final Comparator<Map.Entry<Integer, Integer>> F_COMPARATOR_FN = (left, right) -> {
            int byFrequencyDesc = Integer.compare(right.getValue(), left.getValue());
            if (byFrequencyDesc != 0) {
                return byFrequencyDesc;
            }
            return Integer.compare(left.getKey(), right.getKey());
        };

    @Override
    public List<Frequency> toFrequencyDistributionSortedByN(List<Integer> data) {
        return toSortedFrequency(data, N_COMPARATOR_FN);
    }

    @Override
    public List<Frequency> toFrequencyDistributionSortedByF(List<Integer> data) {
        return toSortedFrequency(data, F_COMPARATOR_FN);
    }

    private List<Frequency> toSortedFrequency(List<Integer> data, Comparator<Map.Entry<Integer, Integer>> comparator) {
        return toFrequencyDistribution(data).entrySet().stream()
            .sorted(comparator)
            .map(e -> new Frequency(e.getKey().intValue(), e.getValue().intValue()))
            .toList();
    }

    private Map<Integer,Integer> toFrequencyDistribution(List<Integer> data) {
        return data.stream()
            .collect(
                Collectors.groupingBy(
                    Function.identity(),
                    HashMap::new,
                    Collectors.summingInt(n -> 1)
                )
            );
    }
}
