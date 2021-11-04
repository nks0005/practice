/*
 * Copyright (c) 2015, 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package org.graalvm.compiler.replacements.nodes;

import org.graalvm.compiler.graph.NodeClass;
import org.graalvm.compiler.nodeinfo.NodeInfo;
import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.spi.Virtualizable;
import org.graalvm.compiler.nodes.spi.VirtualizerTool;
import org.graalvm.compiler.nodes.virtual.VirtualObjectNode;

/**
 * A helper class to allow elimination of byte code instrumentation that could interfere with escape
 * analysis.
 */
@NodeInfo
public class VirtualizableInvokeMacroNode extends MacroStateSplitNode implements Virtualizable {

    public static final NodeClass<VirtualizableInvokeMacroNode> TYPE = NodeClass.create(VirtualizableInvokeMacroNode.class);

    public VirtualizableInvokeMacroNode(MacroParams p) {
        super(TYPE, p);
    }

    @Override
    public void virtualize(VirtualizerTool tool) {
        for (ValueNode arg : arguments) {
            ValueNode alias = tool.getAlias(arg);
            if (alias instanceof VirtualObjectNode) {
                tool.delete();
            }
        }
    }
}
