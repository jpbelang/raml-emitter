package org.raml.validation;

import org.raml.utils.NodeExtractor;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.ResourceLoader;
import org.raml.v2.api.model.v10.RamlFragment;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.api.Library;
import org.raml.v2.api.model.v10.datamodel.ExampleSpec;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Trait;
import org.raml.v2.api.model.v10.resources.ResourceType;
import org.raml.v2.api.model.v10.security.SecurityScheme;
import org.raml.yagi.framework.nodes.Node;

public class Validators {

    //Prevent construction.
    private Validators() {
    }

    public static RamlValidator forApi(Api api, ResourceLoader resourceLoader) {
        return forAnyProxy(api, resourceLoader, RamlFragment.Default);
    }

    public static RamlValidator forTrait(Trait trait, ResourceLoader resourceLoader) {
        return forAnyProxy(trait, resourceLoader, RamlFragment.Trait);
    }

    public static RamlValidator forLibrary(Library library, ResourceLoader resourceLoader) {
        return forAnyProxy(library, resourceLoader, RamlFragment.Library);
    }

    private static RamlValidator forTypeDeclaration(TypeDeclaration typeDeclaration,
        ResourceLoader resourceLoader) {
        return forAnyProxy(typeDeclaration, resourceLoader, RamlFragment.DataType);
    }

    private static RamlValidator forNamedExample(ExampleSpec exampleSpec,
        ResourceLoader resourceLoader) {
        return forAnyProxy(exampleSpec, resourceLoader, RamlFragment.NamedExample);
    }

    private static RamlValidator forAnyProxy(Object exampleSpec, ResourceLoader resourceLoader,
        RamlFragment dataType) {
        Node rootNode = NodeExtractor.extractNodeFromProxy(exampleSpec);
        return RamlV10Validator.create(rootNode, resourceLoader, dataType);
    }

    private static RamlValidator forResourceType(ResourceType resourceType,
        ResourceLoader resourceLoader) {
        return forAnyProxy(resourceType, resourceLoader, RamlFragment.ResourceType);
    }

    private static RamlValidator forSecurityScheme(SecurityScheme securityScheme,
        ResourceLoader resourceLoader) {
        return forAnyProxy(securityScheme, resourceLoader, RamlFragment.SecurityScheme);
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
            case DataType:
                return forTypeDeclaration(ramlModelResult.getTypeDeclaration(), resourceLoader);
            case NamedExample:
                return forNamedExample(ramlModelResult.getExampleSpec(), resourceLoader);
            case ResourceType:
                return forResourceType(ramlModelResult.getResourceType(), resourceLoader);
            case SecurityScheme:
                return forSecurityScheme(ramlModelResult.getSecurityScheme(), resourceLoader);
            //Unsupported yet in the parser
            case DocumentationItem:
            case AnnotationTypeDeclaration:
            case Overlay:
            case Extension:
            default:
                throw new UnsupportedOperationException("fragment not yet supported: " + fragment);
        }

    }
}
