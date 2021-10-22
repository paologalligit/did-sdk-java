package com.hedera.hashgraph.identity.hcs.presenter;

import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.identity.hcs.view.VcDocumentJsonView;

public interface Presenter<T extends HcsVcDocumentBase<?>, U extends VcDocumentJsonView<?>> {
    U fromEntityToDocument(T documentBase);
    T fromDocumentToEntity(U jsonView);
}
