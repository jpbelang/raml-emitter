package org.raml.emitter;

import java.io.IOException;

/**
 * Created by Jean-Philippe Belanger on 9/5/16.
 */
public interface RamlWriter {

    void writeProperty(String key, String value) throws IOException;

    void writeNode(String nodeName) throws IOException;

    RamlWriter childWriter();

    void version(String version) throws IOException;


}
