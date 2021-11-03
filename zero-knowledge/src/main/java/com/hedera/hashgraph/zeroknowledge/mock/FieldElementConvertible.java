package com.hedera.hashgraph.zeroknowledge.mock;

public interface FieldElementConvertible extends AutoCloseable {
    FieldElement toFieldElement() throws MerkleTreeLeafException;
}