package org.raml.emitter;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by Jean-Philippe Belanger on 9/5/16.
 */
public class RamlWriterImpl implements RamlWriter {

    private final Writer writer;
    private final int level;

    public RamlWriterImpl(Writer writer) {

        this.writer = writer;
        this.level = 0;
    }

    public RamlWriterImpl(Writer writer, int level) {
        this.writer = writer;
        this.level = level;
    }

    @Override public void writeProperty(String key, String value) throws IOException {

        tabItUp(level, writer);
        writer.write(key + ": " + value + "\n");
    }

    @Override public void writeNode(String nodeName) throws IOException {

        tabItUp(level, writer);
        writer.write(nodeName + ":\n");
    }

    @Override public RamlWriter childWriter() {
        return new RamlWriterImpl(writer, level + 1);
    }

    @Override public void version(String version) throws IOException {

        writer.write("#%RAML " + version + "\n");
    }

    private static void tabItUp(int depth, Writer writer) throws IOException {
        for (int a = 0; a < depth; a++) {

            writer.write("  ");
        }
    }

    private static String tabItUp(int depth) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int a = 0; a < depth; a++) {

            sb.append("  ");
        }

        return sb.toString();
    }

}
