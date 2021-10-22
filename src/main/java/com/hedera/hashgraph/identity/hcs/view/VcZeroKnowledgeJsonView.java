package com.hedera.hashgraph.identity.hcs.view;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;

import java.util.Map;

public class VcZeroKnowledgeJsonView<T extends CredentialSubject> extends VcDocumentJsonView<T> {
    public static final String[] JSON_ORDER = {"context", "id", "type", "credentialSchema", "credentialSubject", "issuer",
            "issuanceDate", "proof"};

    public VcZeroKnowledgeJsonView(Map<String, Object> documentProperties) {
        super(JSON_ORDER);
    }

    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
