package org.raml.builders;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.raml.builders.node.NodeBuilder;
import org.raml.v2.internal.impl.commons.nodes.MethodNode;
import org.raml.yagi.framework.nodes.Node;
import org.raml.yagi.framework.nodes.StringNode;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MethodBuilderTest {

    private MethodBuilder builder;

    @Mock private NodeBuilder<MethodNode> nodeBuilder;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);

        builder = new MethodBuilder(nodeBuilder);
    }

    @Test public void testConstructor() {
        assertSame(nodeBuilder, builder.getMethodNodeBuilder());
        assertNull(builder.getName()); //No default value.
    }

    @Test public void testWithName() {
        String name = "are you kidding me?";
        assertSame(name, builder.withName(name).getName());
    }

    @Test public void testBuild() {
        String name = "talking to me?";

        MethodNode node = mockedNode();
        when(nodeBuilder.build()).thenReturn(node);

        MethodNode result = builder.withName(name).build();

        assertSame(result, node);
        InOrder inOrder = Mockito.inOrder(result);

        ArgumentCaptor<Node> childrenCaptor = ArgumentCaptor.forClass(Node.class);
        inOrder.verify(result, times(2)).addChild(childrenCaptor.capture());

        List<Node> children = childrenCaptor.getAllValues();
        assertEquals(2, children.size());

        Node firstChild = children.get(0);

        assertTrue(firstChild instanceof StringNode);
        assertSame(name, ((StringNode) firstChild).getValue());

        //for now, we don't assert anything on the second node since the body is not
        //supported yet.
    }

    private MethodNode mockedNode() {
        return mock(MethodNode.class);
    }
}
