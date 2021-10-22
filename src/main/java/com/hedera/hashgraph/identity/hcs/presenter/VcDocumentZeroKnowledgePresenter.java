package com.hedera.hashgraph.identity.hcs.presenter;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentJsonProperties;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentZeroKnowledge;
import com.hedera.hashgraph.identity.hcs.view.VcZeroKnowledgeJsonView;

import java.util.LinkedHashMap;
import java.util.Map;

public class VcDocumentZeroKnowledgePresenter<T extends CredentialSubject> implements Presenter<HcsVcDocumentZeroKnowledge<T>, VcZeroKnowledgeJsonView<T>> {
    @Override
    public VcZeroKnowledgeJsonView<T> fromEntityToDocument(HcsVcDocumentZeroKnowledge<T> documentBase) {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put(HcsVcDocumentJsonProperties.ID, documentBase.getId());
        map.put(HcsVcDocumentJsonProperties.CONTEXT, documentBase.getContext());
        map.put(HcsVcDocumentJsonProperties.TYPE, documentBase.getType());
        map.put(HcsVcDocumentJsonProperties.CREDENTIAL_SUBJECT, documentBase.getCredentialSubject());
        map.put(HcsVcDocumentJsonProperties.ISSUER, documentBase.getIssuer());
        map.put(HcsVcDocumentJsonProperties.ISSUANCE_DATE, documentBase.getIssuanceDate());
        map.put(HcsVcDocumentJsonProperties.PROOF, documentBase.getSignature());

        return new VcZeroKnowledgeJsonView<>(map);
    }

    @Override
    public HcsVcDocumentZeroKnowledge<T> fromDocumentToEntity(VcZeroKnowledgeJsonView<T> jsonView) {
        HcsVcDocumentZeroKnowledge<T> zeroKnowledgeDocument = new HcsVcDocumentZeroKnowledge<>();

        zeroKnowledgeDocument.setId(jsonView.getId());
        zeroKnowledgeDocument.setContext(jsonView.getContext());
        zeroKnowledgeDocument.setType(jsonView.getType());
        zeroKnowledgeDocument.setCredentialSubject(jsonView.getCredentialSubject());
        zeroKnowledgeDocument.setIssuer(jsonView.getIssuer());
        zeroKnowledgeDocument.setIssuanceDate(jsonView.getIssuanceDate());
        zeroKnowledgeDocument.setSignature(jsonView.getSignature());

        return zeroKnowledgeDocument;
    }
}
