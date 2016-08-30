package org.raml.emitter;

import org.raml.yagi.framework.nodes.KeyValueNodeImpl;
import org.raml.yagi.framework.nodes.ObjectNode;
import org.raml.yagi.framework.nodes.ObjectNodeImpl;
import org.raml.yagi.framework.nodes.StringNodeImpl;

/**
 * Created by ebeljea on 8/29/16.
 * Copyright Ericsson.
 */
public class BaseBuilder {
    protected void createProperty(ObjectNode restNode, String name, String value) {
        ObjectNodeImpl descNode = new ObjectNodeImpl();
        restNode.addChild(new KeyValueNodeImpl(new StringNodeImpl(name), descNode));
        descNode.addChild(new KeyValueNodeImpl(new StringNodeImpl("value"), new StringNodeImpl(value)));
    }
}