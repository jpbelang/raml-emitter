package org.raml.emitter;

import org.raml.yagi.framework.nodes.ArrayNode;
import org.raml.yagi.framework.nodes.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by Jean-Philippe Belanger on 9/5/16.
 */
public class RamlWriterImpl implements RamlWriter {

    private final Writer writer;
    private final String directory;
    private final int level;

    public RamlWriterImpl(Writer writer, String directory) {

        this.writer = writer;
        this.directory = directory;
        this.level = 0;
    }

    private RamlWriterImpl(Writer writer, int level, String directory) {
        this.writer = writer;
        this.level = level;
        this.directory = directory;
    }

    @Override public void writeProperty(String key, String value) throws IOException {

        tabItUp(level, writer);
        if (!value.contains("\n")) {
            writer.write(key + ": " + value + "\n");
        } else {
            String newValue = value.replace("\n", "\n" + tabItUp(level + 1));
            writer.write(key + ": |\n");
            tabItUp(level + 1, writer);
            writer.write(newValue + "\n");
        }
    }

    @Override public void writeNode(String nodeName) throws IOException {

        tabItUp(level, writer);
        writer.write(nodeName + ":\n");
    }

    @Override public void rawWrite(String value) throws IOException {

        writer.write(tabItUp(level) + value);
    }

    @Override public RamlWriter childWriter() {
        return new RamlWriterImpl(writer, level + 1, directory);
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

    @Override public void writeToFile(String name, Node refNode) throws IOException {

        try {
            FileWriter fw = new FileWriter(new File(directory, name));
            Emitter.emitLibrary(refNode, new RamlWriterImpl(fw, name));
            fw.close();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override public void writeArray(String s, ArrayNode an) throws IOException {
        writer.write(tabItUp(level));
        writer.write(s + ": ");
        writer.write("[ ");
        for (Node node : an.getChildren()) {
            Emitter.emitNode(node, this);
        }
        // writer.write(tabItUp(level));
        writer.write(" ]\n");
    }

    @Override public void writeSequence(String s, ArrayNode an) throws IOException {
        writer.write(tabItUp(level));
        writer.write(s + ": \n");
        for (Node node : an.getChildren()) {
            writer.write(tabItUp(level + 1) + "-\n");
            Emitter.emitNode(node, this.childWriter().childWriter());  // hugh
        }
    }

}
