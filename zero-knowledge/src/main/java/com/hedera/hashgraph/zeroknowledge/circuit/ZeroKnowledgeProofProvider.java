package com.hedera.hashgraph.zeroknowledge.circuit;

import com.hedera.hashgraph.zeroknowledge.circuit.model.ProofPublicInput;

public interface ZeroKnowledgeProofProvider {
    byte[] createProof(ProofPublicInput proofPublicInput);
    boolean verifyProof(byte[] proof);
}
