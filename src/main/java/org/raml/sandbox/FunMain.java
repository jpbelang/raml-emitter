package org.raml.sandbox;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.raml.builders.BuilderModule;
import org.raml.builders.MethodBuilder;
import org.raml.builders.ResourceBuilder;
import org.raml.builders.node.NodeBuilderModule;
import org.raml.builders.proxy.ModelProxyBuilderModule;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.resources.Resource;

/**
 * Created by ebeljea on 8/27/16.
 * Copyright Ericsson.
 */
public class FunMain {
    public static String raml1 =
        "#%RAML 1.0 \n" + "title: Pet shop\n" + "version: 1\n" + "baseUri: /shop\n" + " \n"
            + "/pets:\n" + "  description: this is it\n" + "  get:\n" + "    responses:\n"
            + "      200:\n" + "        body:\n" + "          application/json:\n" + "  post:\n"
            + "    body:\n" + "      application/json:\n" + "        type: object\n"
            + "        properties:\n" + "          name: string\n" + "          kind: string\n"
            + "          price: number\n" + " \n" + "  /{id}:\n" + "    put:\n" + "      body:\n"
            + "        application/json:\n" + "    delete:\n" + "      responses:\n"
            + "        400:";

    public static void main(String[] args) {



        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(raml1, ".");
        if (ramlModelResult.hasErrors()) {
            for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
                System.out.println(validationResult.getMessage());
            }
        } else {
            Api api = ramlModelResult.getApiV10();
            System.err.println("displayName " + api.resources().get(0).description().value());
            System.err.println("methods: " + api.resources().get(0).methods());
        }


        Injector injector = Guice.createInjector(new BuilderModule(), new ModelProxyBuilderModule(),
            new NodeBuilderModule());
        ResourceBuilder resourceBuilder = injector.getInstance(ResourceBuilder.class);
        MethodBuilder methodBuilder = injector.getInstance(MethodBuilder.class);
        Resource r = resourceBuilder.withResourcePath("/baah").withDisplayName("sheep")
            .withDescription("It works!").withMethods(methodBuilder.withName("post")).build();

        System.err.println("this " + r.description().value());
        System.err.println("this " + r.displayName().value());
        System.err.println("this " + r.relativeUri().value());
        System.err.println("methods: " + r.methods());
    }
}
