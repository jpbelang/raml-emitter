package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public class EmptyLeafRecognizer extends AbstractLeafRecognizer implements Recognizer {

    @Override public boolean looksLike(Node node) {
        return node.getChildren().size() == 0;
    }

    @Override public String getFragment(Node node) {
        return node.toString() + ":";
    }


}
