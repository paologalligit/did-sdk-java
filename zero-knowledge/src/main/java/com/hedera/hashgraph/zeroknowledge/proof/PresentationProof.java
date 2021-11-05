package com.hedera.hashgraph.zeroknowledge.proof;

public interface PresentationProof {
    String getType();
    String getSignature();
    String getProof();
}
