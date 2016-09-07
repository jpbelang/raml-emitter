package org.raml.factories;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.raml.factories.proxy.ModelProxyFactoryModule;
import org.raml.v2.api.model.v10.declarations.AnnotationRef;

public class Factories {

    public Factory<AnnotationRef> annotationRefFactory() {
        return InjectorHolder.injector
            .getInstance(Key.get(new TypeLiteral<Factory<AnnotationRef>>() {
            }));
    }


    private static class InjectorHolder {
        private static final Injector injector =
            Guice.createInjector(new ModelProxyFactoryModule(), new FactoryModule());
    }
}
