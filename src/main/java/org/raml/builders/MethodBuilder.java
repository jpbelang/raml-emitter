package org.raml.builders;

import com.google.common.annotations.VisibleForTesting;
import org.raml.builders.node.NodeBuilder;
import org.raml.v2.internal.impl.commons.nodes.MethodNode;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 */
public class MethodBuilder extends BaseBuilder<MethodNode> {

    private final NodeBuilder<MethodNode> nodeBuilder;

    private String name;

    @VisibleForTesting MethodBuilder(NodeBuilder<MethodNode> nodeBuilder) {
        this.nodeBuilder = nodeBuilder;
    }

    static MethodBuilder create(NodeBuilder<MethodNode> nodeBuilder) {
        checkNotNull(nodeBuilder);

        return new MethodBuilder(nodeBuilder);
    }

    public MethodBuilder withName(String name) {

        this.name = name;
        return this;
    }

    @Override public MethodNode build() {

        MethodNode methodNode = nodeBuilder.build();
        createKey(methodNode, name);
        createValue(methodNode);
        return methodNode;
    }

    @VisibleForTesting
    String getName() {
        return name;
    }

    @VisibleForTesting
    NodeBuilder<MethodNode> getMethodNodeBuilder() {
        return nodeBuilder;
    }
}
