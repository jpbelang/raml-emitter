package org.raml.builders;

import org.omg.CORBA.Object;
import org.raml.yagi.framework.nodes.KeyValueNodeImpl;
import org.raml.yagi.framework.nodes.ObjectNode;
import org.raml.yagi.framework.nodes.ObjectNodeImpl;
import org.raml.yagi.framework.nodes.StringNodeImpl;

/**
 * Created by ebeljea on 8/29/16.
 * Copyright Ericsson.
 */
abstract class BaseBuilder<T> implements Builder<T>{
    protected void createProperty(ObjectNode restNode, String name, String value) {
        ObjectNodeImpl descNode = new ObjectNodeImpl();
        restNode.addChild(new KeyValueNodeImpl(new StringNodeImpl(name), descNode));
        descNode.addChild(new KeyValueNodeImpl(new StringNodeImpl("value"), new StringNodeImpl(value)));
    }

    //TODO: redo this interface better. The value MUST be present on a KeyValueNodeImpl
    //it seems to be of type SYObjectNode or something when parsed...
    protected void createKey(KeyValueNodeImpl node, String key) {
        node.addChild(new StringNodeImpl(key));
    }

    protected void createValue(KeyValueNodeImpl node) {
        node.addChild(new ObjectNodeImpl());
    }
}
