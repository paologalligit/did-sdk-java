package com.hedera.hashgraph.zeroknowledge.mock;

public class FieldElement implements AutoCloseable {
    public static FieldElement deserialize(byte[] fieldElementBytes) throws FieldElementException {
        return new FieldElement();
    }
    public void close() {}
}