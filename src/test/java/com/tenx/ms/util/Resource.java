package com.tenx.ms.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper to read resource files and optionally do placeholder substitutions on them
 */
public class Resource {
    private final String fileName;
    private Object[] substitutions;


    public Resource(String filename) {
        this.fileName = filename;
    }

    public Resource(String filename, Object... substitutions) {
        this.fileName = filename;
        this.substitutions = substitutions;
    }

    public String read() throws URISyntaxException, IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (url == null) {
            throw new IllegalArgumentException("Filename '" + fileName + "' not found");
        }

        return doSubtitutions(FileUtils.readFileToString(new File(url.toURI())), substitutions);
    }

    private String doSubtitutions(String original, Object... subtitutions) {
        if (subtitutions == null) {
            return original;
        }

        Map<Object, Object> subs = MapUtils.putAll(new HashMap(), subtitutions);

        String response = original;
        for (Map.Entry entry : subs.entrySet()) {
            response = response.replace("$" + entry.getKey() + "$", entry.getValue().toString());
        }

        return response;
    }
}
