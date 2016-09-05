package org.raml.emitter;

import org.raml.v2.api.model.v10.api.Api;
import org.raml.yagi.framework.nodes.Node;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by ebeljea on 8/29/16.
 * Copyright Ericsson.
 */
public class Emitter {

    public static void emit(Api api, Writer writer) throws NoSuchFieldException, IllegalAccessException, IOException {

        InvocationHandler handler = Proxy.getInvocationHandler(api);
        Field o = handler.getClass().getDeclaredField("delegate");
        o.setAccessible(true);
        org.raml.v2.internal.impl.commons.model.Api delegate = (org.raml.v2.internal.impl.commons.model.Api) o.get(handler);

        writer.write("#%RAML 1.0\n");
        for (Node child : delegate.getNode().getChildren()) {

            emit(0, child, writer);
        }
    }

    private static void emit(int depth, Node node, Writer writer) throws IOException {

        Recognizer[] recogs =
                {new TypeRecognizer(), new PropertyRecognizer(), new SimpleTypeRecognizer(), new NullNodeRecognizer(),
                        new EmptyLeafRecognizer(),
                        new ContainingNodeRecognizer(), new NotRecognizer()};

        Recognizer pr = selectRecognizer(recogs, node);
        if (pr.looksLike(node)) {
            tabItUp(depth, writer);
            String fragment = pr.getFragment(node, tabItUp(depth + 1));
            writer.write(fragment + /* "# " + "[" + pr + "] " + */"\n");
            for (Node childNode : pr.getChildren(node)) {

                emit(depth + 1, childNode, writer);
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

    private static void tabItUp(int depth, Writer writer) throws IOException {
        for (int a = 0; a < depth; a++) {

            writer.write("  ");
        }
    }

    private static String tabItUp(int depth) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int a = 0; a < depth; a++) {

            sb.append("  ");
        }

        return sb.toString();
    }

}
