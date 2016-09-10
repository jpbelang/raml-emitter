package org.raml.integration;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.raml.utils.FieldExtractor;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.ResourceLoader;
import org.raml.validation.RamlV10Validator;
import org.raml.validation.RamlValidator;
import org.raml.validation.Validators;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class) public class ValidationTest {

    private final URL ramlFile;

    public ValidationTest(URL ramlFile) {
        this.ramlFile = ramlFile;
    }

    @Parameterized.Parameters(name = "raml fiel: {0}")
    public static Collection<Object[]> parameters() throws IOException {
        Pattern ramlPattern = Pattern.compile(".*\\.raml");

        //Get all raml files in resources.
        Set<String> ramlFiles =
            new Reflections("", new ResourcesScanner()).getResources(ramlPattern);

        List<Object[]> parameters = Lists.newArrayList();
        for (String ramlFile : ramlFiles) {

            URL url = ValidationTest.class.getClassLoader().getResource(ramlFile);
            if (canParse(url)) {
                parameters.add(new Object[] {url});
            }
        }
        return parameters;
    }

    private static boolean canParse(URL url) {
        try {
            return !new RamlModelBuilder().buildApi(new File(url.toURI())).hasErrors();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return false;
        }
    }

    @Test public void testValidationWorksAfterParsing()
        throws URISyntaxException, NoSuchFieldException, IllegalAccessException {
        RamlModelBuilder ramlModelBuilder = new RamlModelBuilder();
        ResourceLoader resourceLoader =
            FieldExtractor.getFieldValue("resourceLoader", ramlModelBuilder);
        RamlModelResult ramlModelResult = ramlModelBuilder.buildApi(new File(ramlFile.toURI()));

        if (ramlModelResult.hasErrors()) {
            fail("couldn't parse raml file: " + ramlFile);
        }

        RamlValidator validator = Validators.forResult(ramlModelResult, resourceLoader);
        assertTrue(validator.isValid());
    }
}


