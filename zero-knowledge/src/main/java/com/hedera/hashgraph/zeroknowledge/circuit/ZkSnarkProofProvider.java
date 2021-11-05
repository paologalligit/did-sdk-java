package com.hedera.hashgraph.zeroknowledge.circuit;

import com.hedera.hashgraph.zeroknowledge.circuit.model.ProofPublicInput;

import java.nio.charset.StandardCharsets;

public class ZkSnarkProofProvider implements ZeroKnowledgeProofProvider {
    @Override
    public byte[] createProof(ProofPublicInput proofPublicInput) {
        return "fake-zkSnarkProof".getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean verifyProof(byte[] proof) {
        return true;
    }
}
