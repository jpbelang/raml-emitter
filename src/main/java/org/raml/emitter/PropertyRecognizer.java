package org.raml.emitter;

import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.NullNode;
import org.raml.yagi.framework.nodes.ObjectNode;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public class PropertyRecognizer implements Recognizer {
    @Override public boolean looksLike(Node node) {

        if (!(node instanceof KeyValueNode)) {
            return false;
        }

        if (node.getChildren().size() != 2) {

            return false;
        }

        if (!(node.getChildren().get(1) instanceof ObjectNode)) {

            return false;
        }

        if (node.getChildren().get(1).getChildren().size() != 1 || !(node.getChildren().get(1).getChildren()
                .get(0) instanceof KeyValueNode)) {

            return false;
        }

        KeyValueNode value = (KeyValueNode) node.getChildren().get(1).getChildren().get(0);
        return value.getKey().toString().equals("value");
    }

    @Override public String getFragment(Node node) {

        KeyValueNode kvn = (KeyValueNode) node;
        KeyValueNode valueNode = (KeyValueNode) node.getChildren().get(1).getChildren().get(0);
        if (valueNode instanceof NullNode) {
            return kvn.getKey() + ":";
        } else {
            return kvn.getKey() + ": " + valueNode.getValue();
        }
    }
}
