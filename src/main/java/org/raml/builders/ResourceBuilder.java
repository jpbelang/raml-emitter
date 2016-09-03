package org.raml.builders;

import com.google.common.annotations.VisibleForTesting;
import org.raml.builders.node.NodeBuilder;
import org.raml.builders.proxy.ModelProxyBuilder;
import org.raml.v2.api.model.v10.resources.Resource;
import org.raml.v2.internal.impl.commons.nodes.ResourceNode;
import org.raml.yagi.framework.nodes.ObjectNode;
import org.raml.yagi.framework.nodes.ObjectNodeImpl;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ebeljea on 8/27/16.
 * Copyright Ericsson.
 */
public class ResourceBuilder extends BaseBuilder<Resource> {

    private final ModelProxyBuilder<Resource> modelProxyBuilder;
    private final NodeBuilder<ResourceNode> resourceNodeBuilder;

    private String description;
    private String resourcePath;
    private String displayName;
    private MethodBuilder[] methodBuilders;

    @VisibleForTesting ResourceBuilder(ModelProxyBuilder<Resource> modelProxyBuilder,
        NodeBuilder<ResourceNode> resourceNodeBuilder) {

        this.modelProxyBuilder = modelProxyBuilder;
        this.resourceNodeBuilder = resourceNodeBuilder;
    }

    static ResourceBuilder create(ModelProxyBuilder<Resource> modelProxyBuilder,
        NodeBuilder<ResourceNode> resourceNodeBuilder) {

        checkNotNull(modelProxyBuilder);
        checkNotNull(resourceNodeBuilder);

        return new ResourceBuilder(modelProxyBuilder, resourceNodeBuilder);
    }

    public ResourceBuilder withResourcePath(String resourcePath) {

        this.resourcePath = resourcePath;
        return this;
    }

    public ResourceBuilder withDescription(String description) {

        this.description = description;
        return this;
    }

    public ResourceBuilder withDisplayName(String displayName) {

        this.displayName = displayName;
        return this;
    }

    public ResourceBuilder withMethods(MethodBuilder... methodbuilders) {

        this.methodBuilders = methodbuilders;
        return this;
    }

    @Override public Resource build() {

        ResourceNode rn = resourceNodeBuilder.build();
        createKey(rn, resourcePath);

        ObjectNode restNode = new ObjectNodeImpl();
        rn.addChild(restNode);


        if (displayName == null) {
            createProperty(restNode, "displayName", resourcePath);
        } else {
            createProperty(restNode, "displayName", displayName);
        }

        if (description != null) {
            createProperty(restNode, "description", description);
        }

        for (MethodBuilder methodBuilder : methodBuilders) {
            rn.getValue().addChild(methodBuilder.build());
        }

        org.raml.v2.internal.impl.commons.model.Resource resource =
            new org.raml.v2.internal.impl.commons.model.Resource(rn);
        return modelProxyBuilder.buildForNode(resource);
    }
}