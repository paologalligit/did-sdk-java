package com.hedera.hashgraph.zeroknowledge.presenter;

import com.hedera.hashgraph.zeroknowledge.vp.HcsVpDocumentBase;
import com.hedera.hashgraph.zeroknowledge.vp.VerifiableCredentialBase;

public interface VpPresenter<T extends HcsVpDocumentBase<? extends VerifiableCredentialBase>> {
    String fromDocumentToString(T vpDocument);

    T fromStringToDocument(String stringDocument);
}
