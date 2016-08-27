package org.raml.emitter;

import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;

/**
 * Created by ebeljea on 8/27/16.
 * Copyright Ericsson.
 */
public class FunMain {
    public static String raml1 = "#%RAML 1.0 \n"
            + "title: Pet shop\n"
            + "version: 1\n"
            + "baseUri: /shop\n"
            + " \n"
            + "/pets:\n"
            + "  get:\n"
            + "    responses:\n"
            + "      200:\n"
            + "        body:\n"
            + "          application/json:\n"
            + "  post:\n"
            + "    body:\n"
            + "      application/json:\n"
            + "        type: object\n"
            + "        properties:\n"
            + "          name: string\n"
            + "          kind: string\n"
            + "          price: number\n"
            + " \n"
            + "  /{id}:\n"
            + "    put:\n"
            + "      body:\n"
            + "        application/json:\n"
            + "    delete:\n"
            + "      responses:\n"
            + "        204:";

    public static void main(String[] args) {


        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(raml1, ".");
        if (ramlModelResult.hasErrors())
        {
            for (ValidationResult validationResult : ramlModelResult.getValidationResults())
            {
                System.out.println(validationResult.getMessage());
            }
        }
        else
        {
            Api api = ramlModelResult.getApiV10();

            System.err.println(api.resources().get(0).resourcePath());
        }
    }
}
