package me.jamieburns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped 
@Named(FrequencyDistributionService5.VERSION)
public final class FrequencyDistributionService5 implements FrequencyDistributionService {

    public static final String VERSION = "FDS5";

    public String version() {
        return VERSION;
    }

    @Override
    public List<Frequency> toFrequencyDistributionSortedByN(List<Integer> data) {
        
        return toFrequencyList(data).stream()
            .sorted((l, r) -> Integer.compare(l.n(), r.n()))
            .toList();
    }

    @Override
    public List<Frequency> toFrequencyDistributionSortedByF(List<Integer> data) {
        return toFrequencyList(data).stream()
            .sorted((l, r) -> {
                var a = Integer.compare(r.f(), l.f());
                if( a != 0 ) {
                    return a;
                }
                return Integer.compare(l.n(), r.n());
            })
            .toList();
    }

    private List<Frequency> toFrequencyList(List<Integer> data) {
        return toFrequencyDistribution(data).entrySet().stream()
            .map(e -> new Frequency(e.getKey(), e.getValue()))
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
