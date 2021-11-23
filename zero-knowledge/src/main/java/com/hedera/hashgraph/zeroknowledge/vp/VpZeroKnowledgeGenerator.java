package com.hedera.hashgraph.zeroknowledge.vp;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.zeroknowledge.circuit.ZeroKnowledgeProofProvider;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.VpDocumentGeneratorException;
import com.hedera.hashgraph.zeroknowledge.exception.ZeroKnowledgeProofProviderException;

import java.util.Map;

// TODO: should we add another couple of X extends Y to the vpGenerator?
public abstract class VpZeroKnowledgeGenerator<T extends HcsVcDocumentBase<? extends CredentialSubject>, U extends HcsVpDocumentBase<? extends VerifiableCredential>>
        implements VpGenerator<T, U> {
    private final ZeroKnowledgeProofProvider zeroKnowledgeProofProvider;

    public VpZeroKnowledgeGenerator(ZeroKnowledgeProofProvider zeroKnowledgeProofProvider) {
        this.zeroKnowledgeProofProvider = zeroKnowledgeProofProvider;
    }

    protected byte[] generateSnarkProof(T document, Map<String, Object> presentationMetadata) throws VpDocumentGeneratorException {
        ZeroKnowledgeProofPublicInput publicInput = getProofPublicInput(document, presentationMetadata);
        try {
            return zeroKnowledgeProofProvider.createProof(publicInput);
        } catch (ZeroKnowledgeProofProviderException e) {
            throw new VpDocumentGeneratorException(
                    String.format("Cannot generate verifiable presentation from document: %s", document),
                    e
            );
        }
    }

    protected abstract ZeroKnowledgeProofPublicInput getProofPublicInput(T document, Map<String, Object> presentationMetadata);
}
