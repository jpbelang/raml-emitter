package org.raml.validation;

import org.raml.utils.NodeExtractor;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.ResourceLoader;
import org.raml.v2.api.model.v10.RamlFragment;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.api.Library;
import org.raml.v2.api.model.v10.methods.Trait;
import org.raml.yagi.framework.nodes.Node;

public class Validators {

    //Prevent construction.
    private Validators() {}

    public static RamlValidator forApi(Api api, ResourceLoader resourceLoader) {
        Node rootNode = NodeExtractor.extractNodeFromProxy(api);
        return RamlV10Validator.create(rootNode, resourceLoader, RamlFragment.Default);
    }

    public static RamlValidator forTrait(Trait trait, ResourceLoader resourceLoader) {
        Node rootNode = NodeExtractor.extractNodeFromProxy(trait);
        return RamlV10Validator.create(rootNode, resourceLoader, RamlFragment.Trait);
    }

    public static RamlValidator forLibrary(Library library, ResourceLoader resourceLoader) {
        Node rootNode = NodeExtractor.extractNodeFromProxy(library);
        return RamlV10Validator.create(rootNode, resourceLoader, RamlFragment.Library);
    }

    public static RamlValidator forResult(RamlModelResult ramlModelResult,
        ResourceLoader resourceLoader) {

        if (ramlModelResult.isVersion08()) {
            throw new UnsupportedOperationException("validation of v0.8 not yet supported");
        }

        RamlFragment fragment = ramlModelResult.getFragment();

        switch (fragment) {
            case Default:
                return forApi(ramlModelResult.getApiV10(), resourceLoader);
            case Trait:
                return forTrait(ramlModelResult.getTrait(), resourceLoader);
            case Library:
                return forLibrary(ramlModelResult.getLibrary(), resourceLoader);
            case DocumentationItem:
            case DataType:
            case NamedExample:
            case ResourceType:
            case AnnotationTypeDeclaration:
            case Overlay:
            case Extension:
            case SecurityScheme:
            default:
                throw new UnsupportedOperationException("fragment not yet supported: " + fragment);
        }

    }
}
