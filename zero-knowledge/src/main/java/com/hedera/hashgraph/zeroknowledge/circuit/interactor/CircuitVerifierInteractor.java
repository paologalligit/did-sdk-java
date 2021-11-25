package com.hedera.hashgraph.zeroknowledge.circuit.interactor;

import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitVerifyPublicInput;

public interface CircuitVerifierInteractor<T extends CircuitVerifyPublicInput> {
    boolean verifyProof(T circuitVerifyPublicInput);
}
