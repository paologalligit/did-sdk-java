package com.hedera.hashgraph.zeroknowledge.circuit;

import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.ZeroKnowledgeVerifyProviderException;

public interface ZeroKnowledgeVerifierProvider<V extends ZeroKnowledgeVerifyPublicInput> {
    boolean verifyProof(V publicInput) throws ZeroKnowledgeVerifyProviderException;
}
