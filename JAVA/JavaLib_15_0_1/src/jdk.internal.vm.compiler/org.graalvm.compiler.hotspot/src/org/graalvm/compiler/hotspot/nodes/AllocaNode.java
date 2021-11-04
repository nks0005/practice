/*
 * Copyright (c) 2012, 2019, Oracle and/or its affiliates. All rights reserved.
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


package org.graalvm.compiler.hotspot.nodes;

import static org.graalvm.compiler.nodeinfo.NodeCycles.CYCLES_2;
import static org.graalvm.compiler.nodeinfo.NodeSize.SIZE_1;

import org.graalvm.compiler.core.common.type.StampFactory;
import org.graalvm.compiler.graph.NodeClass;
import org.graalvm.compiler.lir.VirtualStackSlot;
import org.graalvm.compiler.nodeinfo.NodeInfo;
import org.graalvm.compiler.nodes.FixedWithNextNode;
import org.graalvm.compiler.nodes.spi.LIRLowerable;
import org.graalvm.compiler.nodes.spi.NodeLIRBuilderTool;
import org.graalvm.compiler.word.Word;
import org.graalvm.compiler.word.WordTypes;

import jdk.vm.ci.meta.Value;

/**
 * Reserves a block of memory in the stack frame of a method. The block is reserved in the frame for
 * the entire execution of the associated method.
 */
@NodeInfo(cycles = CYCLES_2, size = SIZE_1)
public final class AllocaNode extends FixedWithNextNode implements LIRLowerable {

    public static final NodeClass<AllocaNode> TYPE = NodeClass.create(AllocaNode.class);
    /**
     * The number of slots in block.
     */
    protected final int slots;

    public AllocaNode(@InjectedNodeParameter WordTypes wordTypes, int slots) {
        super(TYPE, StampFactory.forKind(wordTypes.getWordKind()));
        this.slots = slots;
    }

    @Override
    public void generate(NodeLIRBuilderTool gen) {
        VirtualStackSlot array = gen.getLIRGeneratorTool().allocateStackSlots(slots);
        Value result = gen.getLIRGeneratorTool().emitAddress(array);
        gen.setResult(this, result);
    }

    @NodeIntrinsic
    public static native Word alloca(@ConstantNodeParameter int slots);
}
