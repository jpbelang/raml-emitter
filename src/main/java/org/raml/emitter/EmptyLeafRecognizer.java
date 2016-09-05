package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

import java.io.IOException;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public class EmptyLeafRecognizer extends AbstractLeafRecognizer implements Recognizer {

    @Override public boolean looksLike(Node node) {
        return node.getChildren().size() == 0;
    }

    @Override public void writeNode(Node node, RamlWriter writer) throws IOException {

        writer.writeNode(node.toString());
    }


}
