package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public class NotRecognizer implements Recognizer {
    @Override public boolean looksLike(Node node) {

        return false;
    }

    @Override public String getFragment(Node node) {

        return "how did I get here";
    }
}
