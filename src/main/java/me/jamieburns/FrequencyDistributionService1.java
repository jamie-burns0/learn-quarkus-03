package me.jamieburns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named(FrequencyDistributionService1.VERSION)
final class FrequencyDistributionService1 implements FrequencyDistributionService {

    public static final String VERSION = "FDS1";

    public String version() {
        return VERSION;
    }

    @Override
    public List<Frequency> toFrequencyDistributionSortedByN(List<Integer> data) {
        var map = data.stream().collect(
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
    public List<Frequency> toFrequencyDistributionSortedByF(List<Integer> data) {
        return toFrequencyDistributionSortedByN(data).stream()
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
