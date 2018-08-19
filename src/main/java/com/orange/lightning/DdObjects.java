package com.orange.lightning;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public abstract class DdObjects {
    abstract List<DdObject> objects();
}
