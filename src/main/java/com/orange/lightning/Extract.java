package com.orange.lightning;

import com.orange.lightning.spells.ImmutableSpellExtraction;
import com.orange.lightning.spells.SpellExtraction;

import java.io.IOException;

public class Extract {
    public static void main(String[] args) {
        String spellsUrl = "https://www.dnd-spells.com/spells";
        String outDir = System.getProperty("user.home");

        Extract extract = new Extract();
        extract.extractSpells(spellsUrl, outDir + "/Desktop/spells.json");
    }

    private void extractSpells(String url, String path) {
        try {
            SpellExtraction extraction = ImmutableSpellExtraction.builder().parentUrl(url).build();
            DdObjects spells = ImmutableDdObjects.copyOf(extraction.extractChildPages());
            if (spells != null) {
                JsonWriter.writeJson(path, spells);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
