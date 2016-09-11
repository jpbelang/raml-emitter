package org.raml.factories.proxy;

import org.raml.yagi.framework.model.NodeModel;

public interface ModelProxyFactory<T> {
    T buildForNode(NodeModel node);
}
