package org.raml.mutators;

import com.google.common.annotations.VisibleForTesting;
import org.raml.utils.NodeExtractor;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.declarations.AnnotationRef;
import org.raml.v2.api.model.v10.system.types.AnnotableSimpleType;
import org.raml.yagi.framework.nodes.Node;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ApiMutator {

    private final Node node;

    @VisibleForTesting
    ApiMutator(Node node) {
        this.node = node;
    }

    public ApiMutator setTitle(AnnotableSimpleType<String> title) {
        throw new UnsupportedOperationException("unimplemented yet");
    }

    public ApiMutator setTitle(final String title) {
        return setTitle(new AnnotableSimpleType<String>() {
            @Override public String value() {
                return title;
            }

            @Override public List<AnnotationRef> annotations() {
                return Collections.emptyList();
            }
        });
    }

    public static ApiMutator forApi(Api v10Api) {
        checkNotNull(v10Api);

        Node node = NodeExtractor.extractNodeFromProxy(v10Api);
        return new ApiMutator(node);
    }
}
