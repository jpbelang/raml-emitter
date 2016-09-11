package org.raml.utils;

import org.raml.yagi.framework.model.NodeModel;
import org.raml.yagi.framework.nodes.Node;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.raml.utils.FieldExtractor.getFieldValue;

public class NodeExtractor {

    public static <T extends Node> T extractNodeFromProxy(Object object) {
        checkNotNull(object);
        checkArgument(object instanceof Proxy);

        Proxy proxy = (Proxy) object;
        InvocationHandler handler = Proxy.getInvocationHandler(proxy);
        String delegateFieldIdentifier = "delegate";

        try {
            NodeModel nodeModel = getFieldValue(delegateFieldIdentifier, handler);
            return (T) nodeModel.getNode();
        } catch (NoSuchFieldException e) {
            throw new NodeExtractionException("unable to extract field: " + delegateFieldIdentifier, e);
        } catch (IllegalAccessException e) {
            throw new NodeExtractionException("unable to extract field: " + delegateFieldIdentifier, e);
        }
    }
}
