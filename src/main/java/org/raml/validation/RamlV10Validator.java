package org.raml.validation;

import org.raml.v2.api.loader.ResourceLoader;
import org.raml.v2.api.model.v10.RamlFragment;
import org.raml.v2.internal.impl.commons.RamlHeader;
import org.raml.v2.internal.impl.commons.phase.*;
import org.raml.v2.internal.impl.v10.grammar.Raml10Grammar;
import org.raml.v2.internal.impl.v10.phase.AnnotationValidationPhase;
import org.raml.v2.internal.impl.v10.phase.ExampleValidationPhase;
import org.raml.v2.internal.impl.v10.phase.LibraryLinkingTransformation;
import org.raml.v2.internal.impl.v10.phase.MediaTypeInjectionPhase;
import org.raml.v2.internal.utils.RamlNodeUtils;
import org.raml.v2.internal.utils.RamlTreeNodeDumper;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.phase.GrammarPhase;
import org.raml.yagi.framework.phase.Phase;
import org.raml.yagi.framework.phase.TransformationPhase;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class RamlV10Validator implements RamlValidator {

    private final Node api;
    private final ResourceLoader resourceLoader;
    private final RamlFragment fragment;

    private RamlV10Validator(Node api, ResourceLoader resourceLoader, RamlFragment fragment) {
        this.api = api;
        this.resourceLoader = resourceLoader;
        this.fragment = fragment;
    }

    static RamlV10Validator create(Node rootNode, ResourceLoader resourceLoader,
        RamlFragment fragment) {

        checkNotNull(rootNode);
        checkNotNull(resourceLoader);
        checkNotNull(fragment);

        return new RamlV10Validator(rootNode, resourceLoader, fragment);
    }

    @Override public boolean isValid() {
        Node rootNode = api;

        //TODO: find a way to parse the raml fragment.
        final List<Phase> phases = createPhases(resourceLoader, fragment);
        rootNode = runPhases(rootNode, phases);

        //List the errors.
        //        List<ErrorNode> errors = rootNode.findDescendantsWith(ErrorNode.class);
        //        if (!errors.isEmpty())
        //        {
        //            logErrors(errors);
        //            return;
        //        }
        //
        return !RamlNodeUtils.isErrorResult(rootNode);
    }

    private Node runPhases(Node rootNode, List<Phase> phases) {
        for (int i = 0; i < phases.size(); i++) {
            Phase phase = phases.get(i);
            rootNode = phase.apply(rootNode);

            checkDumpPhases(i, phase, rootNode);
            if (RamlNodeUtils.isErrorResult(rootNode)) {
                break;
            }
        }
        return rootNode;
    }

    private void checkDumpPhases(int i, Phase phase, Node rootNode) {
        if (Boolean.getBoolean("dump.phases")) {
            String dump = new RamlTreeNodeDumper().dump(rootNode);
            System.out.println("===============================================================");
            System.out.println("After phase = " + i + " --- " + phase.getClass());
            System.out.println("---------------------------------------------------------------");
            System.out.println(dump);
            System.out.println("---------------------------------------------------------------");
        }
    }

    private List<Phase> createPhases(ResourceLoader resourceLoader, RamlFragment fragment) {
        // The first phase expands the includes.
        final TransformationPhase includePhase =
            new TransformationPhase(new IncludeResolver(resourceLoader),
                new StringTemplateExpressionTransformer());

        final TransformationPhase ramlFragmentsValidator =
            new TransformationPhase(new RamlFragmentGrammarTransformer(resourceLoader));

        // Runs Schema. Applies the Raml rules and changes each node for a more specific. Annotations Library TypeSystem
        final GrammarPhase grammarPhase = new GrammarPhase(RamlHeader.getFragmentRule(fragment));

        // Detect invalid references. Library resourceTypes and Traits. This point the nodes are good enough for Editors.

        // sugar
        // Normalize resources and detects duplicated ones and more than one use of url parameters. ???
        final TransformationPhase libraryLink =
            new TransformationPhase(new LibraryLinkingTransformation(resourceLoader));

        final TransformationPhase referenceCheck =
            new TransformationPhase(new ReferenceResolverTransformer());

        // Applies resourceTypes and Traits Library
        final TransformationPhase resourcePhase =
            new TransformationPhase(new ResourceTypesTraitsTransformer(new Raml10Grammar()));

        final TransformationPhase duplicatedPaths =
            new TransformationPhase(new DuplicatedPathsTransformer());

        // Check unused uri parameters
        final TransformationPhase checkUnusedParameters =
            new TransformationPhase(new UnusedParametersTransformer());

        // Run grammar again to re-validate tree

        final AnnotationValidationPhase annotationValidationPhase =
            new AnnotationValidationPhase(resourceLoader);

        final MediaTypeInjectionPhase mediaTypeInjection = new MediaTypeInjectionPhase();

        // Schema Types example validation
        final TransformationPhase schemaValidationPhase =
            new TransformationPhase(new SchemaValidationTransformer(resourceLoader));

        // Checks types consistency and custom facets
        final TypeValidationPhase typeValidationPhase = new TypeValidationPhase();

        final ExampleValidationPhase exampleValidationPhase =
            new ExampleValidationPhase(resourceLoader);

        return Arrays
            .asList(includePhase, ramlFragmentsValidator, grammarPhase, libraryLink, referenceCheck,
                resourcePhase, duplicatedPaths, checkUnusedParameters, annotationValidationPhase,
                mediaTypeInjection, grammarPhase, schemaValidationPhase, typeValidationPhase,
                exampleValidationPhase);

    }
}
