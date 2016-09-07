package org.raml.mutators;

import org.raml.utils.NodeExtractor;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeInstance;
import org.raml.v2.api.model.v10.declarations.AnnotationRef;
import org.raml.v2.internal.impl.commons.nodes.AnnotationNode;
import org.raml.v2.internal.impl.commons.nodes.AnnotationReferenceNode;

import static com.google.common.base.Preconditions.checkNotNull;

public class AnnotationRefMutator {

    private final AnnotationNode annotationNode;

    public AnnotationRefMutator(AnnotationNode annotationNode) {
        this.annotationNode = annotationNode;
    }

    public static AnnotationRefMutator forAnnotationRef(AnnotationRef annotationRef) {
        checkNotNull(annotationRef);

        AnnotationNode node = NodeExtractor.extractNodeFromProxy(annotationRef);
        return new AnnotationRefMutator(node);
    }

    public AnnotationRefMutator setName(String name) {
        AnnotationReferenceNode annotationReferenceNode =
            (AnnotationReferenceNode) this.annotationNode.getChildren().get(0);

        throw new UnsupportedOperationException("unimplemented yet");
    }

    public AnnotationRefMutator setStruturedValue(TypeInstance typeInstance) {
        throw new UnsupportedOperationException("unimplemented yet");
    }

    public AnnotationRefMutator setAnnotation(TypeDeclaration typeDeclaration) {
        throw new UnsupportedOperationException("unimplemented yet");
    }
}
