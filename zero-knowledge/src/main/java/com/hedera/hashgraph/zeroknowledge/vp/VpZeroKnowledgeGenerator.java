package com.hedera.hashgraph.zeroknowledge.vp;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.zeroknowledge.circuit.ZeroKnowledgeProverProvider;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.VpDocumentGeneratorException;
import com.hedera.hashgraph.zeroknowledge.exception.ZeroKnowledgeProofProviderException;

import java.util.Map;

public abstract class VpZeroKnowledgeGenerator<T extends HcsVcDocumentBase<? extends CredentialSubject>, U extends HcsVpDocumentBase<? extends VerifiableCredential>, P extends ZeroKnowledgeProofPublicInput>
        implements VpGenerator<T, U> {
    private final ZeroKnowledgeProverProvider<P> zeroKnowledgeProofProvider;

    public VpZeroKnowledgeGenerator(ZeroKnowledgeProverProvider<P> zeroKnowledgeProofProvider) {
        this.zeroKnowledgeProofProvider = zeroKnowledgeProofProvider;
    }

    protected byte[] generateSnarkProof(T document, Map<String, Object> presentationMetadata) throws VpDocumentGeneratorException {
        P publicInput = getProofPublicInput(document, presentationMetadata);
        try {
            return zeroKnowledgeProofProvider.createProof(publicInput);
        } catch (ZeroKnowledgeProofProviderException e) {
            throw new VpDocumentGeneratorException(
                    String.format("Cannot generate verifiable presentation from document: %s", document),
                    e
            );
        }
    }

    protected abstract P getProofPublicInput(T document, Map<String, Object> presentationMetadata);
}
