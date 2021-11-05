package com.hedera.hashgraph.zeroknowledge.vp;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.zeroknowledge.circuit.ZeroKnowledgeProofProvider;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ProofPublicInput;

public abstract class VpZkSnarkBuilder<T extends HcsVcDocumentBase<? extends CredentialSubject>, U extends HcsVpDocumentBase<? extends VerifiableCredential>>
        implements VpBuilder<T, U> {
    private final ZeroKnowledgeProofProvider zeroKnowledgeProofProvider;

    public VpZkSnarkBuilder(ZeroKnowledgeProofProvider zeroKnowledgeProofProvider) {
        this.zeroKnowledgeProofProvider = zeroKnowledgeProofProvider;
    }

    protected byte[] generateSnarkProof(ProofPublicInput proofPublicInput) {
        return zeroKnowledgeProofProvider.createProof(proofPublicInput);
    }

    protected boolean verifySnarkProof(byte[] proof) {
        return zeroKnowledgeProofProvider.verifyProof(proof);
    }
}
