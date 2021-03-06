package org.raml.emitter;

import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.NullNode;
import org.raml.yagi.framework.nodes.ObjectNode;

import java.io.IOException;

/**
 * Created by ebeljea on 8/31/16.
 * KeyValue node with an object node as a value.  Handling it as a string right now.
 */
public class ObjectPropertyRecognizer extends AbstractLeafRecognizer implements Recognizer {
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

    @Override public void writeNode(Node node, RamlWriter writer) throws IOException {

        KeyValueNode kvn = (KeyValueNode) node;
        KeyValueNode valueNode = (KeyValueNode) node.getChildren().get(1).getChildren().get(0);

        if (valueNode instanceof NullNode) {
            writer.writeNode(kvn.getKey().toString());
        } else {
            writer.writeProperty(kvn.getKey().toString(), valueNode.getValue().toString());
        }
    }
}
