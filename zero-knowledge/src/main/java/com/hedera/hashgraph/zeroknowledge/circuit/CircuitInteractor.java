package com.hedera.hashgraph.zeroknowledge.circuit;

import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitVerifyPublicInput;

public interface CircuitInteractor {
    void setupCircuit(String provingKeyPath, String verificationKeyPath);
    byte[] generateProof(CircuitProofPublicInput circuitProofPublicInput);
    boolean verifyProof(CircuitVerifyPublicInput circuitVerifyPublicInput);
}
