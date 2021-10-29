package com.hedera.hashgraph.zeroknowledge.mock;

public interface MerkleTreeLeaf extends AutoCloseable {
    FieldElement getLeafAsFieldElement() throws MerkleTreeLeafException;
}