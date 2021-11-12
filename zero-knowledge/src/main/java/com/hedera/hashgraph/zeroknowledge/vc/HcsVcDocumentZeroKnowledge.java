package com.hedera.hashgraph.zeroknowledge.vc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentJsonProperties;
import com.hedera.hashgraph.zeroknowledge.proof.ZeroKnowledgeSignature;

import java.util.LinkedHashMap;
import java.util.Map;

public class HcsVcDocumentZeroKnowledge<T extends CredentialSubject> extends HcsVcDocumentBase<T> {
    @Expose
    @SerializedName(HcsVcDocumentJsonProperties.ZK_SIGNATURE)
    protected ZeroKnowledgeSignature<T> zeroKnowledgeSignature;

    public ZeroKnowledgeSignature<T> getZeroKnowledgeSignature() {
        return zeroKnowledgeSignature;
    }

    public void setZeroKnowledgeSignature(ZeroKnowledgeSignature<T> zeroKnowledgeSignature) {
        this.zeroKnowledgeSignature = zeroKnowledgeSignature;
    }

    @Override
    public Map<String, Object> getCustomHashableFieldsHook() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(HcsVcDocumentJsonProperties.ZK_SIGNATURE, this.zeroKnowledgeSignature.getSignature());
        return map;
    }
}