package com.hedera.hashgraph.identity.hcs.vc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;

public class HcsVcDocumentZeroKnowledge<T extends CredentialSubject> extends HcsVcDocumentBase<T> {
    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVcDocumentJsonProperties.SIGNATURE)
    protected String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public Map<String, Object> addCustomCredentialHashHook() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(HcsVcDocumentJsonProperties.SIGNATURE, this.signature);
        return map;
    }
}
