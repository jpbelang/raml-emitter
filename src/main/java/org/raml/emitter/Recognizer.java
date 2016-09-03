package org.raml.emitter;

import org.raml.yagi.framework.nodes.Node;

import java.util.List;

/**
 * Created by ebeljea on 8/31/16.
 * Copyright Ericsson.
 */
public interface Recognizer {

    boolean looksLike(Node node);

    String getFragment(Node node);

    List<Node> getChildren(Node node);
}
