package org.raml.builders.node;

import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import org.raml.v2.internal.impl.commons.nodes.ResourceNode;

public class NodeBuilderModule extends PrivateModule {

    @Override protected void configure() {
        //Intentionally empty
    }

    @Provides
    @Exposed
    NodeBuilder<ResourceNode> resourceNodeNodeBuilder() {
        return new NodeBuilder<ResourceNode>() {
            @Override public ResourceNode build() {
                return new ResourceNode();
            }
        };
    }
}
