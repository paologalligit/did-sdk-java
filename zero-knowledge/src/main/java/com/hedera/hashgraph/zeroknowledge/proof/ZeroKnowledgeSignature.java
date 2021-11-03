package com.hedera.hashgraph.zeroknowledge.proof;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.sdk.PrivateKey;

public interface ZeroKnowledgeSignature<T extends CredentialSubject> {
    String getSignature();
    void sign(PrivateKey privateKey, HcsVcDocumentBase<T> vcDocument) throws Exception;
}
