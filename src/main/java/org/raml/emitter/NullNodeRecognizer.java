package org.raml.emitter;

import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.NullNode;

import java.io.IOException;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public class NullNodeRecognizer extends AbstractLeafRecognizer implements Recognizer {
    @Override public boolean looksLike(Node node) {

        if (!(node instanceof KeyValueNode)) {
            return false;
        }

        if (node.getChildren().size() != 2) {

            return false;
        }

        return node.getChildren().get(1) instanceof NullNode;

    }

    @Override public void writeNode(Node node, RamlWriter writer) throws IOException {

        KeyValueNode kvn = (KeyValueNode) node;
        writer.writeNode(kvn.getKey().toString());
    }
}
