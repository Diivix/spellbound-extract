package com.orange.lightning;

import com.orange.lightning.spells.ImmutableSpell;
import com.orange.lightning.spells.ImmutableSpellExtraction;
import com.orange.lightning.spells.Spell;
import com.orange.lightning.spells.SpellExtraction;
import org.junit.Assert;
import org.junit.Test;

public class ExtractTest {
    @Test
    public void extractSpell() {
        String url = "https://www.dnd-spells.com/spell/clone";
        String outFilePath = "build/tmp/spells.json";

        SpellExtraction extraction = ImmutableSpellExtraction.builder().parentUrl(url).build();

        Spell spell = ImmutableSpell.copyOf(extraction.extractFromPage(url, 0));

        Assert.assertNotNull(spell);

        DdObjects spells = ImmutableDdObjects.builder().addObjects(spell).build();
        JsonWriter.writeJson(outFilePath, spells);
    }
}