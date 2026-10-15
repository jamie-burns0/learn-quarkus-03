package me.jamieburns;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection 
public record Frequency(int n, int f) {}