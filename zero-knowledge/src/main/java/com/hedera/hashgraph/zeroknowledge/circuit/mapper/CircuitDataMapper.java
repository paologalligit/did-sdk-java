package com.hedera.hashgraph.zeroknowledge.circuit.mapper;

import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.CircuitPublicInputMapperException;

public interface CircuitDataMapper<P extends ZeroKnowledgeProofPublicInput, V extends ZeroKnowledgeVerifyPublicInput> {
    CircuitProofPublicInput fromPublicInputProofToCircuitInputProof(P publicInput) throws CircuitPublicInputMapperException;
    CircuitVerifyPublicInput fromPublicInputVerifyToCircuitInputVerify(V publicInput) throws CircuitPublicInputMapperException;
}
