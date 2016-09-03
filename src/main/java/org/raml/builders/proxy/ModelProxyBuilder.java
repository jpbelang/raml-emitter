package org.raml.builders.proxy;

import org.raml.yagi.framework.model.NodeModel;

public interface ModelProxyBuilder<T> {
    T buildForNode(NodeModel node);
}
