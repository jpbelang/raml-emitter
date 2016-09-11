package org.raml.emitter;

import org.raml.v2.internal.impl.commons.nodes.TypeExpressionNode;
import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;

import java.io.IOException;

/**
 * Created by Jean-Philippe Belanger on 9/4/16.
 * Specific recognition for "type", I'll see if I can generalize.
 */
public class TypeRecognizer extends AbstractLeafRecognizer {

    @Override public boolean looksLike(Node node) {

        if (!(node instanceof KeyValueNode)) {

            return false;
        }

        if (node.getChildren().size() < 2) {

            return false;
        }

        KeyValueNode kvn = (KeyValueNode) node;

        return kvn.getKey().toString().equals("type") && kvn.getValue() instanceof TypeExpressionNode;
    }

    @Override public void writeNode(Node node, RamlWriter writer) throws IOException {
        writer.writeProperty("type", ((TypeExpressionNode) node.getChildren().get(1)).getTypeExpressionText());
    }
}
