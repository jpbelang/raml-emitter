package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public interface Recognizer {

    boolean looksLike(Node node);

    String getFragment(Node node);
}
