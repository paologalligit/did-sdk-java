package com.hedera.hashgraph.identity.hcs.vc.marshaller;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;

public interface VcMarshaller<T extends HcsVcDocumentBase<? extends CredentialSubject>> {
    String fromDocumentToString(T vcDocument);
    T fromStringToDocument(String stringDocument);
}