package org.raml.factories;

import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.raml.factories.proxy.ModelProxyFactory;
import org.raml.v2.api.model.v10.declarations.AnnotationRef;

public class FactoryModule extends PrivateModule {

    @Override protected void configure() {
        //empty on purpose.
    }

    @Provides
    @Exposed
    @Singleton
    Factory<AnnotationRef> annotationRefFactory(ModelProxyFactory<AnnotationRef> proxyFactory) {
        return new AnnotationRefFactory(proxyFactory);
    }
}
