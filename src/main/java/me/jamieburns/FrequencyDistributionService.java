package me.jamieburns;

import java.util.List;

sealed interface FrequencyDistributionService 
    permits FrequencyDistributionService1, FrequencyDistributionService2, FrequencyDistributionService3 
{
    List<Frequency> toFrequencyDistributionSortedByN(List<Integer> list);
    List<Frequency> toFrequencyDistributionSortedByF(List<Integer> list);
}
