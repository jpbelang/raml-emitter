package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public class NotRecognizer extends AbstractLeafRecognizer implements Recognizer {
    @Override public boolean looksLike(Node node) {

        return true;
    }

    @Override public String getFragment(Node node) {

        return "";
    }
}
