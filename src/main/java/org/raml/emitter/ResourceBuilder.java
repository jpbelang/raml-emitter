package org.raml.emitter;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.resources.Resource;
import org.raml.v2.internal.impl.commons.model.DefaultModelElement;
import org.raml.v2.internal.impl.commons.model.StringType;
import org.raml.v2.internal.impl.commons.model.factory.TypeDeclarationModelFactory;
import org.raml.v2.internal.impl.commons.nodes.ResourceNode;
import org.raml.yagi.framework.model.DefaultModelBindingConfiguration;
import org.raml.yagi.framework.model.ModelBindingConfiguration;
import org.raml.yagi.framework.model.ModelProxyBuilder;
import org.raml.yagi.framework.nodes.StringNodeImpl;

import static org.raml.v2.api.RamlModelBuilder.MODEL_PACKAGE;

/**
 * Created by ebeljea on 8/27/16.
 * Copyright Ericsson.
 */
public class ResourceBuilder {

    private final String resourcePath;
    private String description;
    private String displayName;

    public ResourceBuilder(String resourcePath) {

        this.resourcePath = resourcePath;
    }

    private ModelBindingConfiguration createV10Binding() {
        final DefaultModelBindingConfiguration bindingConfiguration = new DefaultModelBindingConfiguration();
        bindingConfiguration.bindPackage(MODEL_PACKAGE);
        // Bind all StringTypes to the StringType implementation they are only marker interfaces
        bindingConfiguration.bind(org.raml.v2.api.model.v10.system.types.StringType.class, StringType.class);
        bindingConfiguration.bind(org.raml.v2.api.model.v10.system.types.ValueType.class, StringType.class);
        bindingConfiguration.defaultTo(DefaultModelElement.class);
        bindingConfiguration.bind(TypeDeclaration.class, new TypeDeclarationModelFactory());
        bindingConfiguration.reverseBindPackage("org.raml.v2.api.model.v10.datamodel");
        return bindingConfiguration;
    }

    private MethodBuilder[] methodBuilders;

    public static ResourceBuilder create(String resourcePath) {
        return new ResourceBuilder(resourcePath);
    }

    public ResourceBuilder withDescription(String description) {

        this.description = description;
        return this;
    }

    public ResourceBuilder withDisplayNamer(String displayName) {

        this.displayName = displayName;
        return this;
    }

    public ResourceBuilder withMethods(MethodBuilder... methodbuilders) {

        this.methodBuilders = methodbuilders;
        return this;
    }

    public Resource create() {

        ResourceNode rn = new ResourceNode();
        rn.addChild(new StringNodeImpl("/goo"));
        org.raml.v2.internal.impl.commons.model.Resource resource = new org.raml.v2.internal.impl.commons.model.Resource(rn);
        return ModelProxyBuilder.createModel(Resource.class, resource, createV10Binding());
    }
}
