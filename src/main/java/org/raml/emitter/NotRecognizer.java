package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

import java.util.List;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public class NotRecognizer extends AbstractLeafRecognizer implements Recognizer {
    @Override public boolean looksLike(Node node) {

        return true;
    }

    @Override public void writeNode(Node node, RamlWriter writer) {

    }

    @Override public List<Node> getChildren(Node n) {
        return n.getChildren();
    }
}
