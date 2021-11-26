package com.hedera.hashgraph.zeroknowledge.marshaller;

import com.hedera.hashgraph.zeroknowledge.vp.HcsVpDocumentBase;
import com.hedera.hashgraph.zeroknowledge.vp.VerifiableCredentialBase;

public interface VpMarshaller<T extends HcsVpDocumentBase<? extends VerifiableCredentialBase>> {
    String fromDocumentToString(T vpDocument);

    T fromStringToDocument(String stringDocument);
}
