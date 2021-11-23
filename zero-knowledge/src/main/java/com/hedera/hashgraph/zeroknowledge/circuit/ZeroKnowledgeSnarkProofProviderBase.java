package com.hedera.hashgraph.zeroknowledge.circuit;

import com.hedera.hashgraph.zeroknowledge.circuit.mapper.CircuitDataMapper;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeVerifyPublicInput;

public abstract class ZeroKnowledgeSnarkProofProviderBase<P extends ZeroKnowledgeProofPublicInput, V extends ZeroKnowledgeVerifyPublicInput> implements ZeroKnowledgeProofProvider<P, V> {
    protected final CircuitInteractor circuitInteractor;
    protected final CircuitDataMapper<P, V> circuitDataMapper;

    public ZeroKnowledgeSnarkProofProviderBase(CircuitInteractor circuitInteractor, CircuitDataMapper<P, V> circuitDataMapper) {
        this.circuitInteractor = circuitInteractor;
        this.circuitDataMapper = circuitDataMapper;
    }
}
