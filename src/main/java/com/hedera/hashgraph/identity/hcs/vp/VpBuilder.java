package com.hedera.hashgraph.identity.hcs.vp;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;

import java.util.List;

public interface VpBuilder<T extends HcsVcDocumentBase<? extends CredentialSubject>, U extends HcsVpDocumentBase<? extends VerifiableCredentialBase>> {
    U generatePresentation(List<T> vcDocuments);
}
