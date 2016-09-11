package org.raml.emitter;

import org.raml.v2.internal.impl.v10.nodes.LibraryLinkNode;
import org.raml.v2.internal.impl.v10.nodes.LibraryNode;
import org.raml.yagi.framework.nodes.Node;

import java.io.IOException;

/**
 * Created by Jean-Philippe Belanger on 9/4/16.
 * Specific recognition for "type", I'll see if I can generalize.
 */
public class LibraryRecognizer extends AbstractLeafRecognizer {

    @Override public boolean looksLike(Node node) {

        if (!(node instanceof LibraryNode)) {

            return false;
        }

        LibraryNode libraryNode = (LibraryNode) node;

        return true;
    }

    @Override public void writeNode(Node node, RamlWriter writer) throws IOException {

        LibraryNode libraryNode = (LibraryNode) node;

        LibraryLinkNode libraryLinkNode = (LibraryLinkNode) libraryNode.getChildren().get(1);
        String name = libraryLinkNode.getRefName();
        writer.writeProperty(libraryNode.getName(), libraryLinkNode.getRefName());
        writer.writeToFile(name, libraryLinkNode.getRefNode());
    }
}
