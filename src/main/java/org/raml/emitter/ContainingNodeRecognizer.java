package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.SimpleTypeNode;

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

    @Override public String getFragment(Node node, String indent) {
        return ((SimpleTypeNode) node.getChildren().get(0)).getValue() + ":";
    }

    @Override public List<Node> getChildren(Node node) {
        if (node.getChildren().size() >= 2) {

            return node.getChildren().get(1).getChildren();
        } else {
            return Collections.emptyList();
        }
    }
}
