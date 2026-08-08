package me.jamieburns;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FrequencyDistributionService {

    Map<Integer,Integer> toFrequencyDistribution(List<Integer> list) {
        return list.stream().collect(
            Collectors.groupingBy(
                n -> n,
                TreeMap::new,
                Collectors.summingInt(n -> 1)
            ));
    }
}
