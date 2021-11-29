package com.hedera.hashgraph.zeroknowledge.proof;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;

/**
 * A generic zero knowledge signature used to sign the verifiable credential document.
 * @param <T> The type of credential subject
 */
public interface ZeroKnowledgeSignature<T extends CredentialSubject> {
    /**
     * @return The signature.
     */
    String getSignature();

    /**
     * Signs the document.
     *
     * @param privateKey The private key used to sign the document.
     * @param vcDocument The verifiable document to sign.
     * @throws Exception Any exception that can occur during the signing process.
     */
    void sign(byte[] privateKey, HcsVcDocumentBase<T> vcDocument) throws Exception;
}
