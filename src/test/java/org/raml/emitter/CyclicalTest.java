package org.raml.emitter;

import org.junit.Test;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.SimpleTypeNode;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Jean-Philippe Belanger on 9/4/16.
 */
public class CyclicalTest {

    public static Api buildModel(String name) throws URISyntaxException, FileNotFoundException {

        URL initial = CyclicalTest.class.getResource(name);
        File f = new File(initial.toURI());
        FileReader fr = new FileReader(f);
        return buildModel(fr, new File(initial.toURI().getPath()).getParentFile().getAbsolutePath());
    }

    public static Api buildModel(Reader reader, String location) {

        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(reader, location);
        if (ramlModelResult.hasErrors()) {
            StringBuilder resultString = new StringBuilder();
            for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
                resultString.append(validationResult);
            }

            throw new AssertionError(resultString.toString());
        } else {
            ramlModelResult.getApiV08();
            return ramlModelResult.getApiV10();
        }
    }

    public static org.raml.v2.internal.impl.commons.model.Api getTopNode(Api api)
            throws NoSuchFieldException, IllegalAccessException {

        InvocationHandler handler = Proxy.getInvocationHandler(api);
        Field o = handler.getClass().getDeclaredField("delegate");
        o.setAccessible(true);
        org.raml.v2.internal.impl.commons.model.Api delegate = (org.raml.v2.internal.impl.commons.model.Api) o.get(handler);

        return delegate;
    }

    private Api reParse(Api api) throws NoSuchFieldException, IllegalAccessException, IOException {
        StringWriter sw = new StringWriter();
        Emitter.emit(api, new RamlWriterImpl(sw));
        System.err.println(sw.toString());
        StringReader sr = new StringReader(sw.toString());

        return buildModel(sr, ".");
    }

    private void compareModels(Api api, Api newApi) throws NoSuchFieldException, IllegalAccessException {

        org.raml.v2.internal.impl.commons.model.Api topApi = getTopNode(api);
        org.raml.v2.internal.impl.commons.model.Api newTop = getTopNode(newApi);
        compareModels(topApi.getNode(), newTop.getNode());

    }

    private void compareModels(Node node, Node newNode) {

        if (node.getChildren().size() != newNode.getChildren().size()) {
            throw new AssertionError("nodes are different");
        }

        for (int i = 0; i < node.getChildren().size(); i++) {

            Node childNode = node.getChildren().get(i);
            Node newChildNode = newNode.getChildren().get(i);
            if (childNode.getType() != newChildNode.getType()) {

                throw new AssertionError(
                        "nodes classes are different: " + childNode.getType() + " " + newChildNode.getType());
            }

            switch (childNode.getType()) {

                case Boolean:
                case String:
                case Integer:
                case Float:
                    compareSimpleNodes((SimpleTypeNode) childNode, (SimpleTypeNode) newChildNode);
                    break;
            }
            compareModels(childNode, newChildNode);
        }
    }

    private void compareSimpleNodes(SimpleTypeNode childNode, SimpleTypeNode newChildNode) {

        if (!childNode.getValue().equals(newChildNode.getValue())) {
            throw new AssertionError("different simple nodes " + childNode.getValue() + " " + newChildNode.getValue());
        }
    }

    @Test
    public void testPetShop() throws Exception {

        Api api = buildModel("/examples/petshop.raml");
        Api newApi = reParse(api);

        compareModels(api, newApi);
    }

    @Test
    public void testHello() throws Exception {

        Api api = buildModel("/examples/helloworld/helloworld.raml");
        Api newApi = reParse(api);

        compareModels(api, newApi);
    }

    @Test
    public void testOrganisationApi() throws Exception {

        Api api = buildModel("/examples/defining-examples/organisation-api.raml");
        Api newApi = reParse(api);

        compareModels(api, newApi);
    }

    @Test
    public void testUsingStrict() throws Exception {

        Api api = buildModel("/examples/defining-examples/using-strict.raml");
        Api newApi = reParse(api);

        compareModels(api, newApi);
    }

}
