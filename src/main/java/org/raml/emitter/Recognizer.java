package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

import java.io.IOException;
import java.util.List;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public interface Recognizer {

    boolean looksLike(Node node);

    void writeNode(Node node, RamlWriter writer) throws IOException;

    List<Node> getChildren(Node node);
}
