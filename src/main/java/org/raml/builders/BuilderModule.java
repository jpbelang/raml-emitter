package org.raml.builders;

import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import org.raml.builders.node.NodeBuilder;
import org.raml.builders.proxy.ModelProxyBuilder;
import org.raml.v2.api.model.v10.resources.Resource;
import org.raml.v2.internal.impl.commons.nodes.MethodNode;
import org.raml.v2.internal.impl.commons.nodes.ResourceNode;

public class BuilderModule extends PrivateModule {

    @Override protected void configure() {
        // Intentionally empty
    }

    @Provides @Exposed ResourceBuilder resourceBuilder(
        ModelProxyBuilder<Resource> modelProxyBuilder, NodeBuilder<ResourceNode> nodeNodeBuilder) {
        return ResourceBuilder.create(modelProxyBuilder, nodeNodeBuilder);
    }

    @Provides @Exposed MethodBuilder methodBuilder(NodeBuilder<MethodNode> methodNodeBuilder) {
        return MethodBuilder.create(methodNodeBuilder);
    }
}
