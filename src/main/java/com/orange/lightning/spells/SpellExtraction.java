package com.orange.lightning.spells;

import com.google.common.collect.ImmutableList;
import com.orange.lightning.DdObjects;
import com.orange.lightning.Extraction;
import com.orange.lightning.ImmutableDdObjects;
import org.immutables.value.Value;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

@Value.Immutable
public abstract class SpellExtraction implements Extraction {
    @Override
    public DdObjects extractChildPages() throws IOException {
        Document doc = Jsoup.connect(parentUrl()).validateTLSCertificates(false).get();

        Element table = doc.body().getElementById("example");
        Elements links = table.select("a");

        Set<String> urlSet = new HashSet<>();
        links.forEach(link -> urlSet.add(link.attr("href")));

        List<Spell> spells = new ArrayList<>();
        int count = 0;
        for (String url : urlSet) {
            Spell spell = extractFromPage(url, count++);
            if (spell != null) {
                spells.add(spell);
            }
        }

        if (!spells.isEmpty()) {
            return ImmutableDdObjects.builder().objects(spells).build();
        }

        return null;
    }

    @Override
    public ImmutableSpell extractFromPage(String url, int id) {
        System.out.println("Processing url: " + url);

        ImmutableList<String> blackListedSpells = ImmutableList.of("trap the soul");

        Document doc;
        try {
            doc = Jsoup.connect(url).validateTLSCertificates(false).get();
            Elements content = doc.body().select("div.page-content");

            String name = content.select("h1.classic-title").first().text();

            // Remove black listed spells
            if(blackListedSpells.contains(name.toLowerCase())) {
                return null;
            }

            String school = content.select("p").get(0).text();
            String tempLevel = content.select("p").get(1).select("strong").get(0).text();
            int level = tempLevel.equalsIgnoreCase("cantrip")
                    ? 0
                    : Integer.parseInt(tempLevel);
            String castingTime = content.select("p").get(1).select("strong").get(1).text();
            String range = content.select("p").get(1).select("strong").get(2).text();
            String tempComponents = content.select("p").get(1).select("strong").get(3).text();
            String duration = content.select("p").get(1).select("strong").get(4).text();
            String description = content.select("p").get(3).text();

            // At Higher Levels and Classes
            String atHigherLevels = "";
            String classTypes = "";
            if (content.select("h4").size() == 1) {
                // Doesn't contain a "at higher level".
                classTypes = content.select("p").get(5).select("a").text();
            } else if (content.select("h4").size() == 2) {
                atHigherLevels = content.select("p").get(4).text();
                classTypes = content.select("p").get(6).select("a").text();
            }

            // Edge case, "at higher level" and "classes" found in slight different spot.
            if (classTypes.equals("")) {
                atHigherLevels = content.select("p").get(5).text();
                classTypes = content.select("p").get(7).select("a").text();
            }

            // Second edge case, classTypes is still empty
            if (classTypes.equals("")) {
                atHigherLevels = content.select("p").get(7).text();
                classTypes = content.select("p").get(9).select("a").text();
            }

            // Components, Materials, and Description
            String[] components;
            String materials = "";
            if (tempComponents.contains("(")) {
                // Edge case, found only materials with no no components. Adding them manually.
                if(tempComponents.startsWith("(")) {
                    tempComponents = "V, M " + tempComponents;
                }

                int beginMaterials = tempComponents.indexOf("(");
                components = tempComponents
                        .substring(0, beginMaterials - 1)
                        .replaceAll(",", "")
                        .split(" ");
                materials = tempComponents.substring(beginMaterials + 1, tempComponents.length() - 1);
            } else if (description.startsWith("(")) {
                components = tempComponents.replaceAll(",", "").split(" ");
                materials = description.substring(1, description.indexOf(")") - 1);
                description = description.substring(description.indexOf(")") + 1, description.length() - 1);
            } else {
                components = tempComponents.replaceAll(",", "").split(" ");
            }

            // Clean components for known errors
            if (components.length == 1 && components[0].equalsIgnoreCase("VSM")) {
                components = new String[]{"V", "S", "M"};
            } else if (components[0].equalsIgnoreCase("V.")) {
                components[0] = "V";
            }

            // Specific spell cases
            if(name.equalsIgnoreCase("clone")) {
                description = content.select("div").get(5).text();
                classTypes = content.select("p").get(6).select("a").text();
                atHigherLevels = "";
            }

            return ImmutableSpell.builder()
                    .id(id)
                    .name(name)
                    .school(school)
                    .level(level)
                    .castingTime(castingTime)
                    .range(range)
                    .duration(duration)
                    .description(description)
                    .atHigherLevels(atHigherLevels)
                    .materials(materials)
                    .classTypes(ImmutableList.copyOf(classTypes.split(" ")))
                    .components(ImmutableList.copyOf(components))
                    .build();

        } catch (Exception e) {
            System.out.println("Error processing: " + url);
            e.printStackTrace();
            return null;
        }
    }
}
