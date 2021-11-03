package com.hedera.hashgraph.zeroknowledge.mock;

public class BaseMerkleTree {
    public static BaseMerkleTree init(int i) {
        return new BaseMerkleTree();
    }

    public void append(FieldElement fieldElement) {
    }

    public void finalizeTreeInPlace() {}

    public BaseMerkleTree finalizeTree() {
        return this;
    }

    public FieldElement root() {
        return new FieldElement();
    }
}
