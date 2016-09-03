package org.raml.builders;

import com.google.common.annotations.VisibleForTesting;
import org.raml.v2.api.model.v10.methods.Method;

/**
 */
class MethodBuilder implements Builder<Method> {

    @VisibleForTesting
    MethodBuilder() {}

    static MethodBuilder create() {
        return new MethodBuilder();
    }

    @Override public Method build() {
        return null;
    }
}
