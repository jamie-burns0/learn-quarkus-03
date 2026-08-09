package me.jamieburns;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FrequencyDistributionService {

    public record FrequencyCount(int n, int f) {}

    Map<Integer,Integer> toFrequencyDistributionSortedByN(List<Integer> list) {
        return list.stream().collect(
            Collectors.groupingBy(
                n -> n,
                TreeMap::new,
                Collectors.summingInt(n -> 1)
            ));
    }

    List<FrequencyCount> toFrequencyDistributionSortedByF(List<Integer> list) {
        return toFrequencyDistributionSortedByN(list).entrySet().stream()
            .map(entry -> new FrequencyCount(entry.getKey(), entry.getValue()))
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
