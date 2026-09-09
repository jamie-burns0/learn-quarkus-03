package me.jamieburns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("FDS3")
final class FrequencyDistributionService3 implements FrequencyDistributionService {

	@Override
	public List<Frequency> toFrequencyDistributionSortedByN(List<Integer> values) {
		var fd = toFrequencyDistribution(values);
        return sortByN(fd);
	}

	@Override
	public List<Frequency> toFrequencyDistributionSortedByF(List<Integer> values) {
		var fd = toFrequencyDistribution(values);
        return sortByF(fd);
	}

    private Map<Integer, Integer> toFrequencyDistribution(List<Integer> data) {
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
            .toList();
    }

    private List<Frequency> sortByF(Map<Integer, Integer> fd) {
        return fd.entrySet().stream()
            .sorted((l, r) -> {
                int c1 = Integer.compare(r.getValue(), l.getValue());
                if(c1 != 0) {
                    return c1;
                }
                return Integer.compare(l.getKey(), r.getKey());
            })
            .map(e -> new Frequency(e.getKey(), e.getValue()))
            .toList();
    }    
}
