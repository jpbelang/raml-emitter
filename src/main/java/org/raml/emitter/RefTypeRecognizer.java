package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.ReferenceNode;

import java.io.IOException;

/**
 * Created by ebeljea on 8/31/16.
 * KeyValue node that holds a simple type as a value.
 */
public class RefTypeRecognizer extends AbstractLeafRecognizer implements Recognizer {
    @Override public boolean looksLike(Node node) {

        return node instanceof ReferenceNode;
    }

    @Override public void writeNode(Node node, RamlWriter writer) throws IOException {

        ReferenceNode ref = (ReferenceNode) node;
        writer.rawWrite(ref.getSource().toString());
    }
}
