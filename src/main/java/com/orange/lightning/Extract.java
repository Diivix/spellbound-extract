package com.orange.lightning;

import com.google.common.collect.ImmutableList;
import com.orange.lightning.spells.ImmutableSpellExtraction;
import com.orange.lightning.spells.Spell;
import com.orange.lightning.spells.SpellExtraction;

import java.io.IOException;

public class Extract {
    public static void main(String[] args) {
        String url = "https://www.dnd-spells.com/spells";
        String outFilePath = "~/Desktop/objects.json";


        try {
            SpellExtraction extraction = ImmutableSpellExtraction.builder().parentUrl(url).build();
            ImmutableList<Spell> spells = ImmutableList.copyOf(extraction.extractChildPages());
            JsonWriter writer = ImmutableJsonWriter.builder()
                    .objects(spells)
                    .outFilePath(outFilePath)
                    .build();
            writer.writeJson();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
