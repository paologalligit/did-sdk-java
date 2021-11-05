package com.hedera.hashgraph.zeroknowledge.vp;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;

import java.util.List;
import java.util.Map;

public interface VpBuilder<T extends HcsVcDocumentBase<? extends CredentialSubject>, U extends HcsVpDocumentBase<? extends VerifiableCredential>> {
    U generatePresentation(List<T> vcDocuments, Map<String, Object> presentationMetadata);
}
