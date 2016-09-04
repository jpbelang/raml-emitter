package org.raml.builders;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.raml.builders.node.NodeBuilderModule;
import org.raml.builders.proxy.ModelProxyBuilderModule;

public class Builders {

    //Idiomatic way to handle lazy initialization.
    private static class InjectorHolder {
        private static Injector injector = Guice
            .createInjector(new BuilderModule(), new ModelProxyBuilderModule(),
                new NodeBuilderModule());
    }

    public static ResourceBuilder resourceBuilder() {
        return InjectorHolder.injector.getInstance(ResourceBuilder.class);
    }

    public static MethodBuilder methodBuilder() {
        return InjectorHolder.injector.getInstance(MethodBuilder.class);
    }
}
