package org.raml.emitter;

import org.raml.yagi.framework.nodes.ArrayNode;
import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.ReferenceNode;

import java.io.IOException;

/**
 * Created by ebeljea on 8/31/16.
 * KeyValue node with an object node as a value.  Handling it as a string right now.
 */
public class ObjectArrayPropertyRecognizer extends AbstractLeafRecognizer implements Recognizer {
    @Override public boolean looksLike(Node node) {

        if (!(node instanceof KeyValueNode)) {
            return false;
        }

        KeyValueNode kvn = (KeyValueNode) node;

        if (!(kvn.getValue() instanceof ArrayNode)) {

            return false;
        }

        if (node.getChildren().size() != 2) {

            return false;
        }

        ArrayNode an = (ArrayNode) kvn.getValue();

        return node.getChildren().get(1) instanceof ArrayNode && an.getChildren().get(0) instanceof ReferenceNode;
    }

    @Override public void writeNode(Node node, RamlWriter writer) throws IOException {

        KeyValueNode kvn = (KeyValueNode) node;
        ArrayNode an = (ArrayNode) kvn.getValue();
        writer.writeArray(kvn.getKey().toString(), an);
    }

}
