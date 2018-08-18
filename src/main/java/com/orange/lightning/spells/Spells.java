package com.orange.lightning.spells;

import com.google.common.collect.ImmutableList;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Spells {
    protected abstract ImmutableList<Spells> spells();
}
