package com.hedera.hashgraph.zeroknowledge.mock;

public class PoseidonHash implements AutoCloseable {
    public static PoseidonHash getInstanceConstantLength(int i) {
        return new PoseidonHash();
    }

    public void update(FieldElement keyField) {

    }

    public FieldElement finalizeHash() {
        return new FieldElement();
    }

    @Override
    public void close() {

    }
}
