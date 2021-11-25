package com.hedera.hashgraph.zeroknowledge.circuit.mapper;

import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.CircuitPublicInputMapperException;

public interface CircuitVerifierDataMapper<V extends ZeroKnowledgeVerifyPublicInput, C extends CircuitVerifyPublicInput> {
    C fromPublicInputVerifyToCircuitInputVerify(V publicInput) throws CircuitPublicInputMapperException;
}
