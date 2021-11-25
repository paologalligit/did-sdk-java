package com.hedera.hashgraph.zeroknowledge.circuit.interactor;

import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitProofPublicInput;

public interface CircuitProverInteractor<T extends CircuitProofPublicInput> {
    byte[] generateProof(T circuitProofPublicInput);
}
