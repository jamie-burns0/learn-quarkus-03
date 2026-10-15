package me.jamieburns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped 
@Named("FDS4")
final public class FrequencyDistributionService4 implements FrequencyDistributionService {

    @Override
    public List<Frequency> toFrequencyDistributionSortedByN(List<Integer> data) {
        return sortByN(toFrequencyDistribution(data));
    }

    @Override
    public List<Frequency> toFrequencyDistributionSortedByF(List<Integer> data) {
        return sortByF(toFrequencyDistribution(data));
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

    private List<Frequency> sortByN(Map<Integer, Integer> fd) {
        return fd.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(e -> new Frequency(e.getKey(), e.getValue()))
            .collect(
                Collectors.toList()
            );
    }

    private List<Frequency> sortByF(Map<Integer, Integer> fd) {
        return fd.entrySet().stream()
            .map(e -> new Frequency(e.getKey(), e.getValue()))
            .sorted( (l,r) -> {
                var c = Integer.compare(r.f(), l.f());
                if(c != 0) {
                    return c;
                }
                return Integer.compare(l.n(), r.n());
            })
            .toList();
    }
}
