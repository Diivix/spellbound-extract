package com.orange.lightning;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public final class JsonWriter {

    public static void writeJson(String path, DdObjects objects) {
        File outFile = new File(path);
        try(Writer writer = new FileWriter(outFile)) {
            outFile.createNewFile();
            Gson gson = new Gson();

            String jsonObjects = gson.toJson(objects);
            writer.write(jsonObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
