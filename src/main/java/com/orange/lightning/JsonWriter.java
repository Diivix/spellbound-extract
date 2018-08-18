package com.orange.lightning;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import org.immutables.value.Value;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

@Value.Immutable
public abstract class JsonWriter {
    abstract String outFilePath();
    abstract ImmutableList<DdObject> objects();

    public void writeJson() {
        File outFile = new File(outFilePath());
        try(Writer writer = new FileWriter(outFile)) {
            outFile.createNewFile();
            Gson gson = new Gson();

            String jsonObjects = gson.toJson(objects());
            writer.write(jsonObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
