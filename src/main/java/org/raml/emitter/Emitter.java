package org.raml.emitter;

import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.internal.impl.commons.nodes.RamlDocumentNode;
import org.raml.yagi.framework.nodes.Node;

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

    public static String output = "#%RAML 1.0\n"
            + "title: Pet shop\n"
            + "version: 1\n"
            + "baseUri: /shop\n"
            + "  /pets:\n"
            + "    description: this is it\n"
            + "      get:\n"
            + "        responses:\n"
            + "          200:\n"
            + "            body:\n"
            + "              application/json:\n"
            + "                type: any\n"
            + "                displayName: application/json\n"
            + "            displayName: 200\n"
            + "        displayName: get\n"
            + "      post:\n"
            + "        body:\n"
            + "          application/json:\n"
            + "            type: object\n"
            + "              properties:\n"
            + "                name:\n"
            + "                  type: string\n"
            + "                  displayName: name\n"
            + "                kind:\n"
            + "                  type: string\n"
            + "                  displayName: kind\n"
            + "                price:\n"
            + "                  type: number\n"
            + "                  displayName: price\n"
            + "            displayName: application/json\n"
            + "        displayName: post\n"
            + "      /{id}:\n"
            + "        put:\n"
            + "          body:\n"
            + "            application/json:\n"
            + "              type: any\n"
            + "              displayName: application/json\n"
            + "          displayName: put\n"
            + "        delete:\n"
            + "          responses:\n"
            + "            400:\n"
            + "          displayName: delete\n"
            + "        displayName: /{id}\n"
            + "    displayName: /pets\n";

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {



        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(raml1, ".");
        if (ramlModelResult.hasErrors()) {
            for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
                System.out.println(validationResult.getMessage());
            }
        } else {
            ramlModelResult.getApiV08();
            Api api = ramlModelResult.getApiV10();
            Emitter.emit(api);
        }


    }

    private static void emit(Api api) throws NoSuchFieldException, IllegalAccessException {

        InvocationHandler handler = Proxy.getInvocationHandler(api);
        Field o = handler.getClass().getDeclaredField("delegate");
        o.setAccessible(true);
        org.raml.v2.internal.impl.commons.model.Api delegate = (org.raml.v2.internal.impl.commons.model.Api) o.get(handler);

        RamlDocumentNode node = (RamlDocumentNode) delegate.getNode();
        System.out.println("#%RAML 1.0");
        emit(0, node);
    }

    private static void emit(int depth, Node n) {

        Recognizer[] recogs =
                {new PropertyRecognizer(), new SimpleTypeRecognizer(), new NullNodeRecognizer(), new LeafRecognizer()};

        for (int i = 0; i < n.getChildren().size(); i++) {

            Node node = n.getChildren().get(i);
            Recognizer pr = selectRecognizer(recogs, node);
            if (pr.looksLike(node)) {
                tabItUp(depth);
                System.out.println(pr.getFragment(node));
            } else {

                emit(depth + 1, node);
            }
        }

    }

    private static Recognizer selectRecognizer(Recognizer[] recogs, Node node) {
        for (Recognizer recog : recogs) {
            if (recog.looksLike(node)) {
                return recog;
            }
        }

        return new NotRecognizer();
    }

    private static void tabItUp(int depth) {
        for (int a = 0; a < depth; a++) {

            System.out.print("  ");
        }
    }

}
