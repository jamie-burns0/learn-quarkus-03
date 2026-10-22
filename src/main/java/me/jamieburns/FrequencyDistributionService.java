package me.jamieburns;

import java.util.List;

sealed interface FrequencyDistributionService 
    permits FrequencyDistributionService1, 
            FrequencyDistributionService2, 
            FrequencyDistributionService3, 
            FrequencyDistributionService4,
            FrequencyDistributionService5
{
    List<Frequency> toFrequencyDistributionSortedByN(List<Integer> data);
    List<Frequency> toFrequencyDistributionSortedByF(List<Integer> data);
    String version();
}
