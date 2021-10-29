package com.hedera.hashgraph.zeroknowledge.vp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hedera.hashgraph.zeroknowledge.proof.ZkSnarkProof;
import com.hedera.hashgraph.zeroknowledge.proof.ZkSnarkProofJsonProperties;

import java.util.LinkedHashMap;
import java.util.Map;

public class VerifiableCredential extends VerifiableCredentialBase {
    @Expose()
    @SerializedName(VerifiableCredentialJsonProperties.PROOF)
    protected ZkSnarkProof zkSnarkProof;

    @Override
    public Map<String, Object> getCustomHashableFieldsHook() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ZkSnarkProofJsonProperties.SIGNATURE, zkSnarkProof.getSignature());
        return map;
    }
}
