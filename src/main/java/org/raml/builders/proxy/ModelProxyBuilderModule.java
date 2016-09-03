package org.raml.builders.proxy;

import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.resources.Resource;
import org.raml.v2.internal.impl.commons.model.DefaultModelElement;
import org.raml.v2.internal.impl.commons.model.StringType;
import org.raml.v2.internal.impl.commons.model.factory.TypeDeclarationModelFactory;
import org.raml.yagi.framework.model.DefaultModelBindingConfiguration;
import org.raml.yagi.framework.model.ModelBindingConfiguration;
import org.raml.yagi.framework.model.NodeModel;

import static org.raml.v2.api.RamlModelBuilder.MODEL_PACKAGE;

public class ModelProxyBuilderModule extends PrivateModule {

    @Override protected void configure() {
        //Hello sonar ;)
    }

    @Provides
    @Singleton
    ModelBindingConfiguration bindingConfiguration() {
        final DefaultModelBindingConfiguration bindingConfiguration =
            new DefaultModelBindingConfiguration();
        bindingConfiguration.bindPackage(MODEL_PACKAGE);
        // Bind all StringTypes to the StringType implementation they are only marker interfaces
        bindingConfiguration
            .bind(org.raml.v2.api.model.v10.system.types.StringType.class, StringType.class);
        bindingConfiguration
            .bind(org.raml.v2.api.model.v10.system.types.ValueType.class, StringType.class);
        bindingConfiguration.defaultTo(DefaultModelElement.class);
        bindingConfiguration.bind(TypeDeclaration.class, new TypeDeclarationModelFactory());
        bindingConfiguration.reverseBindPackage("org.raml.v2.api.model.v10.datamodel");
        return bindingConfiguration;
    }

    @Provides
    @Exposed
    @Singleton
    ModelProxyBuilder<Resource> modelProxyBuilderForResource(final ModelBindingConfiguration configuration) {
        return new ModelProxyBuilder<Resource>() {
            @Override public Resource buildForNode(NodeModel node) {
                return org.raml.yagi.framework.model.ModelProxyBuilder
                    .createModel(Resource.class, node, configuration);
            }
        };
    }
}
