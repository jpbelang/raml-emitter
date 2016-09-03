package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

import java.util.Collections;
import java.util.List;

/**
 * Created by ebeljea on 9/3/16.
 * Copyright Ericsson.
 */
public abstract class AbstractLeafRecognizer implements Recognizer {

    @Override public List<Node> getChildren(Node n) {
        return Collections.emptyList();
    }
}
