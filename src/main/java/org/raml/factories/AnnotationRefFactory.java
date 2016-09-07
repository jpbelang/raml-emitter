package org.raml.factories;

import com.google.common.annotations.VisibleForTesting;
import org.raml.factories.proxy.ModelProxyFactory;
import org.raml.v2.api.model.v10.declarations.AnnotationRef;
import org.raml.v2.internal.impl.commons.nodes.AnnotationNode;

public class AnnotationRefFactory implements Factory<AnnotationRef> {

    private final ModelProxyFactory<AnnotationRef> proxyBuilder;

    @VisibleForTesting
    AnnotationRefFactory(ModelProxyFactory<AnnotationRef> proxyBuilder) {
        this.proxyBuilder = proxyBuilder;
    }

    @Override
    public AnnotationRef create() {
        AnnotationNode annotationNode = new AnnotationNode();
        return proxyBuilder.buildForNode(new org.raml.v2.internal.impl.commons.model.AnnotationRef(annotationNode));
    }
}
