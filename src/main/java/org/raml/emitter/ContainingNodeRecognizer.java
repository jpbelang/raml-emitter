package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.SimpleTypeNode;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by ebeljea on 9/3/16.
 * Copyright Ericsson.
 */
public class ContainingNodeRecognizer implements Recognizer {

    @Override public boolean looksLike(Node node) {

        return (node.getChildren().size() >= 1) && node.getChildren().get(0) instanceof SimpleTypeNode;
    }

    @Override public void writeNode(Node node, RamlWriter writer) throws IOException {
        writer.writeNode(((SimpleTypeNode) node.getChildren().get(0)).getValue().toString());
    }

    @Override public List<Node> getChildren(Node node) {
        if (node.getChildren().size() >= 2) {

            return node.getChildren().get(1).getChildren();
        } else {
            return Collections.emptyList();
        }
    }
}
