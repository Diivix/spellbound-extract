package com.orange.lightning;

import com.google.common.collect.ImmutableList;
import com.orange.lightning.spells.Spell;

import java.io.IOException;

public interface Extraction {
    String parentUrl();

    ImmutableList<Spell> extractChildPages() throws IOException;

    DdObject extractFromPage(String url, int id) throws IOException;
}
