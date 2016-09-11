package org.raml.emitter;

import org.raml.yagi.framework.nodes.ArrayNode;
import org.raml.yagi.framework.nodes.Node;

import java.io.IOException;

/**
 * Created by Jean-Philippe Belanger on 9/5/16.
 */
public interface RamlWriter {

    void writeProperty(String key, String value) throws IOException;

    void writeNode(String nodeName) throws IOException;

    void rawWrite(String value) throws IOException;

    RamlWriter childWriter();

    void version(String version) throws IOException;

    void writeToFile(String name, Node refNode) throws IOException;

    void writeArray(String s, ArrayNode an) throws IOException;
}
