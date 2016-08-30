package org.raml.emitter;

import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.internal.impl.commons.nodes.RamlDocumentNode;
import org.raml.yagi.framework.nodes.KeyValueNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.ObjectNode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by ebeljea on 8/29/16.
 * Copyright Ericsson.
 */
public class Emitter {
    public static String raml1 = "#%RAML 1.0 \n"
            + "title: Pet shop\n"
            + "version: 1\n"
            + "baseUri: /shop\n"
            + " \n"
            + "/pets:\n"
            + "  description: this is it\n"
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
            + "        400:";

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {



        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(raml1, ".");
        if (ramlModelResult.hasErrors()) {
            for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
                System.out.println(validationResult.getMessage());
            }
        } else {
            Api api = ramlModelResult.getApiV10();
            Emitter.emit(api);
        }

    }

    private static void emit(Api api) throws NoSuchFieldException, IllegalAccessException {

        System.err.println(api);
        InvocationHandler handler = Proxy.getInvocationHandler(api);
        Field o = handler.getClass().getDeclaredField("delegate");
        o.setAccessible(true);
        org.raml.v2.internal.impl.commons.model.Api delegate = (org.raml.v2.internal.impl.commons.model.Api) o.get(handler);

        RamlDocumentNode node = (RamlDocumentNode) delegate.getNode();
        emit(0, node);
    }

    private static void emit(int depth, Node n) {

        if (n instanceof KeyValueNode) {
            for (int a = 0; a < depth; a++) {

                System.out.print("\t");
            }

            KeyValueNode kvn = (KeyValueNode) n;
            ObjectNode o = (ObjectNode) kvn.getValue();
            KeyValueNode values = (KeyValueNode) o.getChildren().get(0);
            System.out.println(kvn.getKey() + ": " + values.getValue());
            return;
        }

        for (Node node : n.getChildren()) {

            if (node.getChildren().size() == 0) {

                for (int a = 0; a < depth; a++) {

                    System.out.print("\t");
                }
                System.out.println(node);
            } else {
                emit(depth + 1, node);
            }
        }
    }

}
