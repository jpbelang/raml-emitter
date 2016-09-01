package org.raml.emitter;

import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.SimpleTypeNode;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public class SimpleTypeRecognizer implements Recognizer {
    @Override public boolean looksLike(Node node) {

        if (!(node instanceof KeyValueNode)) {
            return false;
        }

        if (node.getChildren().size() != 2) {

            return false;
        }

        return node.getChildren().get(1) instanceof SimpleTypeNode;

    }

    @Override public String getFragment(Node node) {

        KeyValueNode kvn = (KeyValueNode) node;
        return kvn.getKey() + ": " + node.getChildren().get(1).toString();
    }
}
