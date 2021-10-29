package com.hedera.hashgraph.zeroknowledge.mock;

public class MerkleTreeLeafException extends Exception {
    public MerkleTreeLeafException(String message) {
        super(message);
    }

    public MerkleTreeLeafException(String message, Throwable cause) {
        super(message, cause);
    }
}
