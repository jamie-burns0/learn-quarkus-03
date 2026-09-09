package me.jamieburns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("FDS1")
final class FrequencyDistributionService1 implements FrequencyDistributionService {

    @Override
    public List<Frequency> toFrequencyDistributionSortedByN(List<Integer> list) {
        var map = list.stream().collect(
            Collectors.groupingBy(
                Function.identity(),
                HashMap::new,
                Collectors.summingInt(n -> 1)
            ));

        return map.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> new Frequency(entry.getKey(), entry.getValue()))
            .toList();
    }

    @Override
    public List<Frequency> toFrequencyDistributionSortedByF(List<Integer> list) {
        return toFrequencyDistributionSortedByN(list).stream()
            .sorted((left, right) -> {
                int byFrequencyDesc = Integer.compare(right.f(), left.f());
                if (byFrequencyDesc != 0) {
                    return byFrequencyDesc;
                }
                return Integer.compare(left.n(), right.n());
            })
            .toList();
    }
}
