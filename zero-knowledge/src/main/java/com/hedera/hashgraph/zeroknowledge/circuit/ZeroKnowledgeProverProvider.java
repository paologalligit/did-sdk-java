package com.hedera.hashgraph.zeroknowledge.circuit;

import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.ZeroKnowledgeProofProviderException;

public interface ZeroKnowledgeProverProvider<P extends ZeroKnowledgeProofPublicInput> {
    byte[] createProof(P publicInput) throws ZeroKnowledgeProofProviderException;
}
