package com.orange.lightning.spells;

import com.google.common.collect.ImmutableList;
import com.orange.lightning.DdObject;
import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public abstract class Spell implements DdObject {
    protected abstract String name();
    protected abstract String school();
    protected abstract int level();
    protected abstract ImmutableList<String> classTypes();
    protected abstract String castingTime();
//    protected abstract String castingTimeDescription();
    protected abstract String range();
//    protected abstract String rangeDescription();
    protected abstract ImmutableList<String> components();
    protected abstract String materials();
    protected abstract String duration();
//    protected abstract String durationDescription();
    protected abstract String description();
    protected abstract String atHigherLevels();
}
