package org.raml.emitter;

import org.raml.utils.NodeExtractor;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.internal.impl.commons.nodes.RamlDocumentNode;
import org.raml.yagi.framework.nodes.Node;

import java.io.IOException;

/**
 * Created by ebeljea on 8/29/16.
 * Copyright Ericsson.
 */
public class Emitter {

    public static void emit(Api api, RamlWriter writer) throws IOException {

        //check ça mon JÉPI
        RamlDocumentNode node = NodeExtractor.extractNodeFromProxy(api);

        writer.version("1.0");
        for (Node child : node.getChildren()) {

            emitChildren(child, writer);
        }
    }

    public static void emitLibrary(Node node, RamlWriter writer)
            throws NoSuchFieldException, IllegalAccessException, IOException {

        writer.version("1.0 Library");

        emitChildren(node, writer);
    }

    public static void emitNode(Node node, RamlWriter ramlWriter) throws IOException {

        emitChildren(node, ramlWriter);
    }

    private static void emitChildren(Node node, RamlWriter writer) throws IOException {

        Recognizer[] recogs =
                {new RefTypeRecognizer(), new TypeRecognizer(), new LibraryRecognizer(), new ObjectArrayPropertyRecognizer(),
                        new ObjectPropertyRecognizer(), new SimpleTypeRecognizer()/*, new NullNodeRecognizer()*/,
                        new ContainingNodeRecognizer(), new NotRecognizer()};

        Recognizer pr = selectRecognizer(recogs, node);
        if (pr.looksLike(node)) {
            pr.writeNode(node, writer);
            for (Node childNode : pr.getChildren(node)) {

                emitChildren(childNode, writer.childWriter());
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

}
