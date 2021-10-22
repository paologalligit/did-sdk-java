package com.hedera.hashgraph.identity.hcs.view;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;

import java.util.Map;

public class VcJsonView<T extends CredentialSubject> extends VcDocumentJsonView<T> {
    public static final String[] JSON_ORDER = {"context", "id", "type", "credentialSchema", "credentialSubject", "issuer",
            "issuanceDate"};

    public VcJsonView(Map<String, Object> documentProperties) {
        super(JSON_ORDER);
    }
}
