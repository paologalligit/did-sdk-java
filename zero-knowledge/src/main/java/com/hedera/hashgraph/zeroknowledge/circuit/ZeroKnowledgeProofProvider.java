package com.hedera.hashgraph.zeroknowledge.circuit;

import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.ZeroKnowledgeProofProviderException;
import com.hedera.hashgraph.zeroknowledge.exception.ZeroKnowledgeVerifyProviderException;

public interface ZeroKnowledgeProofProvider<P extends ZeroKnowledgeProofPublicInput, V extends ZeroKnowledgeVerifyPublicInput> {
    byte[] createProof(P publicInput) throws ZeroKnowledgeProofProviderException;
    boolean verifyProof(V publicInput) throws ZeroKnowledgeVerifyProviderException;
}
