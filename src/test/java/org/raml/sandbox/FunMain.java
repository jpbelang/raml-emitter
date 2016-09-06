package org.raml.sandbox;

import com.google.common.base.Joiner;
import org.raml.builders.Builders;
import org.raml.builders.MethodBuilder;
import org.raml.builders.ResourceBuilder;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.resources.Resource;
import org.raml.v2.api.model.v10.system.types.MarkdownString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class FunMain {
    private static final Logger logger = LoggerFactory.getLogger(FunMain.class);

    public static void main(String[] args) throws URISyntaxException {

        if (args.length != 1) {
            throw new IllegalArgumentException("usage: <resourceToParse>");
        }

        URL raml = FunMain.class.getResource(args[0]);

        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(new File(raml.toURI()));

        if (ramlModelResult.hasErrors()) {
            for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
                System.out.println(validationResult.getMessage());
            }
        } else {
            Api api = ramlModelResult.getApiV10();

            List<TypeDeclaration> typeDeclarations = api.types();
            MarkdownString description = api.description();

            logger.info("description: {}", description);
            logger.info("type declarations: [{}]", Joiner.on(", ").join(typeDeclarations));
        }



    }

    private static void previousStuff() {
        ResourceBuilder resourceBuilder = Builders.resourceBuilder();
        MethodBuilder methodBuilder = Builders.methodBuilder();
        Resource r = resourceBuilder.withResourcePath("/baah").withDisplayName("sheep")
            .withDescription("It works!").withMethods(methodBuilder.withName("post")).build();

        System.err.println("this " + r.description().value());
        System.err.println("this " + r.displayName().value());
        System.err.println("this " + r.relativeUri().value());
        System.err.println("methods: " + r.methods());
    }
}
