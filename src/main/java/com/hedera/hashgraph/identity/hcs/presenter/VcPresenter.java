package com.hedera.hashgraph.identity.hcs.presenter;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;

public interface VcPresenter<T extends HcsVcDocumentBase<? extends CredentialSubject>> {
    String fromDocumentToString(T vcDocument);
    T fromStringToDocument(String stringDocument);
}