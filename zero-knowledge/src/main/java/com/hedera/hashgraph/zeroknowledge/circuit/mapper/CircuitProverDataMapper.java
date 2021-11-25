package com.hedera.hashgraph.zeroknowledge.circuit.mapper;

import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.CircuitPublicInputMapperException;

public interface CircuitProverDataMapper<P extends ZeroKnowledgeProofPublicInput, C extends CircuitProofPublicInput> {
    C fromPublicInputProofToCircuitInputProof(P publicInput) throws CircuitPublicInputMapperException;
}
